import java.io.IOException;
import java.nio.file.*;

/**
 * Classe principal para execução do programa que lê arquivos de memória e processa os endereços.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        readFiles();
    }
    /**
     * Lê os arquivos do diretório "files" e processa seu conteúdo.
     */
    public static void readFiles() {
        Path dir = Paths.get("files"); //MUDAR INPUT AQUI
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file)) {
                    Path fileName = file.getFileName();
                    System.out.println("========================================");
                    System.out.printf("Open file: %s\n", fileName);
                    MemoryReader.readMemoryFile(dir.resolve(fileName).toString());
                }
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.printf("Error opening directory: %s\n", e);
        }
    }
}
