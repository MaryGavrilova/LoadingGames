import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Main {
    public static void main(String[] args) {
        openZip("/Users/gavri/Games/savegames/zip.zip", "/Users/gavri/Games/savegames/");
        try {
            GameProgress gameProgress1 = openProgress("/Users/gavri/Games/savegames/save1.dat");
            System.out.println(gameProgress1);
            GameProgress gameProgress2 = openProgress("/Users/gavri/Games/savegames/save2.dat");
            System.out.println(gameProgress2);
            GameProgress gameProgress3 = openProgress("/Users/gavri/Games/savegames/save3.dat");
            System.out.println(gameProgress3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openZip(String zipPath, String extractPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String nameOfFile;
            while ((entry = zin.getNextEntry()) != null) {
                nameOfFile = entry.getName();
                FileOutputStream fout = new FileOutputStream(extractPath + nameOfFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameProgress gameProgress = (GameProgress) ois.readObject();
        fis.close();
        ois.close();
        return gameProgress;
    }
}