package com.philwin.marketdataloader.util

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class FileUtil {
    companion object {
        @JvmStatic
        fun moveFileToFolder(file : File, folder : File) : Boolean {
            var name    =   folder.toPath().resolve(file.name)
            println("Printing the name : ${name.toFile().absolutePath}")
            if (folder.isDirectory && file.isFile) {
                Files.move(Paths.get(file.absolutePath), folder.toPath().resolve(file.name), StandardCopyOption.ATOMIC_MOVE)
                if (Paths.get(folder.absolutePath.plus(File.separator).plus(file.name)).toFile().exists()) {
                    println("File was successfully moved from ${file.absolutePath} to ${folder.absolutePath}")
                    return true
                } else {
                    println("File failed to move from ${file.absolutePath} to ${folder.absolutePath}")
                    return false
                }
            } else {
                println("Either the folder ${folder.absolutePath} is not a folder or the file ${file.absolutePath} is not a file... not moving")
                return false
            }
        }
    }

}