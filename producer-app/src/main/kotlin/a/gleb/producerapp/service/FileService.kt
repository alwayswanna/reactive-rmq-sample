package a.gleb.producerapp.service

import a.gleb.producerapp.logger
import a.gleb.producerapp.model.UserMessage
import kotlinx.coroutines.reactive.awaitFirst
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.io.ByteArrayOutputStream

const val SHEET_POSITION = 0
const val USERNAME_CELL_POS = 0
const val MESSAGE_CELL_POS = 1

@Service
class FileService(private val senderService: SenderService) {

    /**
     * Method reads data from xlsx document
     */
    suspend fun proceedAndSend(file: FilePart) {
        logger.info { "Start proceed file, [filename=${file.filename()}]" }
        val fileInputStream = file.toBytes().inputStream()
        val workBook = XSSFWorkbook(fileInputStream)
        val sheet = workBook.getSheetAt(SHEET_POSITION)

        sheet.rowIterator().forEach {
            val firstCell = it.getCell(USERNAME_CELL_POS)
            val lastCell = it.getCell(MESSAGE_CELL_POS)
            senderService.sendMessage(UserMessage(firstCell.stringCellValue, lastCell.stringCellValue))
        }

        logger.info { "End proceed file, [filename=${file.filename()}]" }
    }

    suspend fun FilePart.toBytes(): ByteArray {
        val bytesList: List<ByteArray> = this.content()
            .flatMap { dataBuffer -> Flux.just(dataBuffer.asByteBuffer().array()) }
            .collectList()
            .awaitFirst()

        val byteStream = ByteArrayOutputStream()
        bytesList.forEach { bytes -> byteStream.write(bytes) }
        return byteStream.toByteArray()
    }
}