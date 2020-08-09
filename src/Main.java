import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        String savePath = "/Users/andrejzemlacev/Desktop/Games/savegames/";
        String zipName = "zip.zip";

        openZip(savePath + zipName, savePath);
        GameProgress gp = openProgress(savePath + "save2.dat");
        if (gp != null) {
            System.out.println(gp);
        } else {
            System.out.println("Соранение не может быть загружено!");
        }
    }

    private static GameProgress openProgress(String s) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(s))) {
            System.out.println("Загрузка сохранения: " + s);
            return (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println("Ошибка открытия сохранения: " + ex.getMessage());
        }
        return null;
    }

    public static void openZip(String zipFileName, String outputPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFileName))) {
            ZipEntry entry;
            String outputFileName;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                outputFileName = outputPath + entry.getName();
                FileOutputStream fileOutputStream = new FileOutputStream(outputFileName);
                int buff;
                while ((buff = zipInputStream.read()) != -1) {
                    fileOutputStream.write(buff);
                }
                fileOutputStream.flush();
                zipInputStream.closeEntry();
                fileOutputStream.close();
                System.out.println("Файл " + outputFileName + " распакован!");
            }
        } catch (Exception ex) {
            System.out.println("Ошибка распаковки : " + ex.getMessage());
        }
    }
}
