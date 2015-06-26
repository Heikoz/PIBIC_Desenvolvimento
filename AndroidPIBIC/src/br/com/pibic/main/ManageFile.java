package br.com.pibic.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class ManageFile {
    private static final String TAG = "ManageFile";
    private Context context;
    private boolean sdCardAvailable;
    private boolean sdCardWritableReadable;
    private boolean sdCardReadableOnly;
    
    public ManageFile(Context context){
        this.context = context;
    }
    
    /**
     * Faz a leitura do arquivo
     * @return O texto lido.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public String readFile() throws FileNotFoundException, IOException{
        File textfile = new File(context.getExternalFilesDir(null),
            "romar.txt");

        FileInputStream input = new FileInputStream(textfile);
        byte[] buffer = new byte[(int)textfile.length()];
        
        input.read(buffer);            
        
        return new String(buffer);
    }
    
    public void getStateSDcard(){
        
        // Obt�m o status do cart�o SD
        String status = Environment.getExternalStorageState();
        
        if (Environment.MEDIA_BAD_REMOVAL.equals(status)) {
            // Midia foi removida antes de ser montada
            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia removida.");
        }
        else if (Environment.MEDIA_CHECKING.equals(status)) {
            // Midia est� presente e est� sendo feita a verifica��o
            sdCardAvailable = true;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia sendo verificada.");
        }
        else if (Environment.MEDIA_MOUNTED.equals(status)) {
            // A midia est� presente e montada neste momento com
            // permiss�o de escrita e leitura
            sdCardAvailable = true;
            sdCardWritableReadable = true;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia com permiss�o de escrita e leitura.");
        }
        else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(status)) {
            // A midia est� presente e montada neste momento com 
            // permiss�o somente de leitura
            sdCardAvailable = true;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia com permiss�o somente leitura.");
        }
        else if (Environment.MEDIA_NOFS.equals(status)) {
            // A midia est� presente, mas est� vazia ou utilizando um
            // sistema de arquivos n�o suportado    
            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia com sistema de arquivos n�o compat�vel.");
        }
        else if (Environment.MEDIA_REMOVED.equals(status)) {
            // A midia n�o est� presente
            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia n�o presente.");
        }
        else if (Environment.MEDIA_SHARED.equals(status)) {
            // A midia est� presente, n�o montada e compartilhada 
            // via USB
            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia compartilhada via USB.");
        }
        else if (Environment.MEDIA_UNMOUNTABLE.equals(status)) {
            // A midia est� presente mas n�o pode ser montada
            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia n�o pode ser montada");
        }
        else if (Environment.MEDIA_UNMOUNTED.equals(status)) {
            // A midia est� presente mas n�o montada
            sdCardAvailable = false;
            sdCardWritableReadable = false;
            sdCardReadableOnly = false;
            Log.d(TAG, "Midia n�o montada.");
        }
    }

    public boolean isSdCardAvailable() {
        return sdCardAvailable;
    }

    public void setSdCardAvailable(boolean sdCardAvailable) {
        this.sdCardAvailable = sdCardAvailable;
    }

    public boolean isSdCardWritableReadable() {
        return sdCardWritableReadable;
    }

    public void setSdCardWritableReadable(boolean sdCardWritableReadable) {
        this.sdCardWritableReadable = sdCardWritableReadable;
    }

    public boolean isSdCardReadableOnly() {
        return sdCardReadableOnly;
    }

    public void setSdCardReadableOnly(boolean sdCardReadableOnly) {
        this.sdCardReadableOnly = sdCardReadableOnly;
    }

}