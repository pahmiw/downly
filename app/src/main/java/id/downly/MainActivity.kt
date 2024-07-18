package id.downly

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.MimeTypeMap.getFileExtensionFromUrl
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.anyShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader
import id.downly.databinding.ActivityMainBinding
import id.downly.extension.createThumbnailFromImage
import id.downly.extension.createThumbnailFromPdf
import id.downly.extension.createThumbnailFromVideo
import id.downly.extension.createUnsupportedFileTypeImage
import id.downly.extension.getFileExtensionFromMimeType
import id.downly.extension.getMimeTypeFromUrl
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private var downloadAdapter: DownloadAdapter? = null

    // TODO handle request
    private val request by lazy {
        permissionsBuilder(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handlePermissionRequest()


        setupView()
        FileDownloader.setup(this)
    }

    private fun handlePermissionRequest() {
        request.addListener { result ->
            when {
                result.anyPermanentlyDenied() -> showToast("permission anyPermanentlyDenied")
                result.anyShouldShowRationale() -> showToast("permission anyShouldShowRationale")
                result.allGranted() -> showToast("permission allGranted")
            }
            Timber.tag("ISINYAA").d("$result")
        }
        request.send()
    }

    private fun setupView() {
        downloadAdapter = DownloadAdapter()
        binding.rvDownloadedItems.layoutManager = GridLayoutManager(this, 2)
        binding.rvDownloadedItems.adapter = downloadAdapter

        binding.tvDownloadItem.setOnClickListener {
            val url = binding.etYourLink.text.toString()
            if (url.isNotEmpty()) {
                scope.launch {
                    downloadFile(url)
                }
            } else {
                showToast("Url Can't be Empty")
            }
        }

        refreshDownloadList()
    }

    private fun refreshDownloadList() {
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val files = folder.listFiles()?.filter { it.name.contains("downly") }
        files?.let {
            val items = mapFilesToItems(it)
            downloadAdapter?.submitData(items)
        }
    }

    private suspend fun downloadFile(url: String) = withContext(Dispatchers.IO) {
        var fileExtension: String? = getFileExtensionFromUrl(url)

        if (fileExtension.isNullOrEmpty()) {
            val mimeType = getMimeTypeFromUrl(url)
            fileExtension = getFileExtensionFromMimeType(mimeType)
        }

        val defaultName = "downly_${System.currentTimeMillis()}"
        val fileName: String =
            if (fileExtension.isNullOrEmpty()) defaultName else "$defaultName.$fileExtension"

        val downloadFolder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val filePath = File(downloadFolder, fileName).absolutePath

        val task = FileDownloader.getImpl().create(url).setPath(filePath)
            .setListener(object : FileDownloadListener() {
                override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    showLoading(true)
                    Timber.tag("ISINYAA").d("pending")
                }

                override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    showLoading(true)
                    Timber.tag("ISINYAA").d("progress bytes $soFarBytes total $totalBytes")
                }

                override fun completed(task: BaseDownloadTask?) {
                    showToast("task completed")
                    showLoading(false)
                    Timber.tag("ISINYAA").d("complete ${task?.tag}, ${task?.filename}")
                    refreshDownloadList()
                }

                override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                    showToast("task has been paused")
                    showLoading(false)
                    Timber.tag("ISINYAA").d("paused")
                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    showToast("there's something wrong")
                    showLoading(false)
                    Timber.tag("ISINYAA").d("error $e")
                }

                override fun warn(task: BaseDownloadTask?) {
                    Timber.tag("ISINYAA").d("warn")
                }
            })

        task.start()
    }

    private fun showLoading(isShowLoading: Boolean) {
        binding.progress.visibility = if (isShowLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun mapFilesToItems(files: List<File>): List<ItemDownloaded> {
        return files.map { file ->
            val name = file.name
            val path = file.absolutePath
//            val mimeType = getFileMimeType(file)
            val thumbnail = generateThumbnail(path, name)

            ItemDownloaded(name, thumbnail, path)
        }
    }

    private fun generateThumbnail(filePath: String, name: String): Bitmap? {
        return when {
            filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png") -> createThumbnailFromImage(
                filePath,
                150
            )

            filePath.endsWith(".pdf") -> createThumbnailFromPdf(filePath, 150)
            filePath.endsWith(".mp4") -> createThumbnailFromVideo(filePath)
            else -> createUnsupportedFileTypeImage(150, 150, name)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        scope.cancel()
    }
}
