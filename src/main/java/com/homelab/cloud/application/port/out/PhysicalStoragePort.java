package com.homelab.cloud.application.port.out;

import java.io.InputStream;

public interface PhysicalStoragePort {

    /**
     * Guarda el archivo en el disco duro y devuelve la ruta física donde quedó guardado.
     */
    String saveFile(InputStream inputStream, String originalFilename, String ownerId);

    /**
     * Elimina el archivo físico del disco (útil para cuando implementemos borrar archivos o si falla una transacción).
     */
    void deleteFile(String physicalPath);

}
