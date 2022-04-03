import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        String dirPathSave = "E:\\IdeaProjects\\Games\\savegames\\";
        String zipNameFile = "zip.zip";
        String zipPathFile = dirPathSave + zipNameFile;
        List<String> filesSaves = new ArrayList<>();

        List<GameProgress> gameProgress = Arrays.asList(
                new GameProgress(99, 5, 15, 18.6),
                new GameProgress(75, 1, 21, 80.1),
                new GameProgress(50, 3, 13, 10.5)
        );

        savingGameProgressObjects(gameProgress, dirPathSave, filesSaves);
        zipFiles(zipPathFile, filesSaves);
        filesSavesDeleting(dirPathSave);
    }

    public static void savingGameProgressObjects(List<GameProgress> gameProgress, String dirPathSave, List<String> filesSaves) {
        for (int i = 0; i < gameProgress.size(); i++) {
            String fileNameSave = "save" + i + ".dat";
            String filePathSave = dirPathSave + fileNameSave;
            saveGame(filePathSave, gameProgress.get(i));
            filesSaves.add(filePathSave);
            System.out.println("Создаем файл сохранений \"" + filePathSave + "\"");
        }
    }

    public static void saveGame(String filePathSave, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePathSave);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage(
            ));
        }
    }

    public static void zipFiles(String zipPathFile, List<String> filesSaves) {
        try (FileOutputStream fos = new FileOutputStream(zipPathFile);
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (String fileSave : filesSaves) {
                File fileZip = new File(fileSave);
                try (FileInputStream fis = new FileInputStream(fileZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileZip.getName());
                    zout.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    System.out.println("Файл \"" + fileSave + "\" добавлен в архив " + "\"" + zipPathFile + "\"");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void filesSavesDeleting(String dirPathSave) {
        Arrays.stream(new File(dirPathSave).listFiles())
                .filter(x -> !x.getName().contains("zip.zip"))
                .forEach(File::delete);
        System.out.println("Файлы сохранений не лежащие в архиве удалены из " + "\"" + dirPathSave + "\"");
    }

}