import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A class responsible for reading and processing memory data files.
 */
public class MemoryReader {

    /**
     * Lê um arquivo de entrada, remove as linhas vazias e processa os dados da memória.
     *
     * @param file O caminho do arquivo a ser lido.
     * @throws IOException Se ocorrer um erro durante a leitura do arquivo.
     */
    public static void readMemoryFile(String file) throws IOException {
        // Use o método para remover linhas vazias e retorne o arquivo processado
        try (BufferedReader reader = new BufferedReader(new FileReader(removeEmptyLines(file)))) {
            String line;

            Double memorySize = 0.0;
            int[] memoryInfo = new int[3];

            // Lê a primeira linha para obter o tamanho da memória
            line = reader.readLine();
            if (line != null) {
                Double tempSize = Double.parseDouble(line);

                // Validação do tamanho da memória
                if (tempSize > 4294967296L) {
                    System.out.println("O endereço de memória é muito grande! Encerrando leitura do arquivo...");
                    return;
                } else {
                    memorySize = tempSize;
                }
            }

            // Lê as três próximas linhas para obter os valores de `memoryInfo`
            for (int i = 0; i < 3;) {
                line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    memoryInfo[i] = Integer.parseInt(line);
                    i++;
                }
            }

            // Lê e processa os endereços de memória
            if ((line = reader.readLine()) != null) {
                if ((memorySize >= 1 && memorySize <= 4294967296L) &&
                    (memoryInfo[0] >= 1 && memoryInfo[0] <= 65536) &&
                    (memoryInfo[1] >= 1 && memoryInfo[1] <= 65536) &&
                    (memoryInfo[2] >= 1 && memoryInfo[2] <= 1024)) {

                    String[] addresses = line.split(" ");

                    if (addresses.length >= 1 && addresses.length <= 2048) {
                        int[] memAddresses = new int[addresses.length]; // Endereços de memória

                        for (int i = 0; i < addresses.length; i++) {
                            int addressValue = Integer.parseInt(addresses[i]);
                            if (addressValue >= 0 && (addressValue <= (memoryInfo[0] - 1) || addressValue <= memorySize)) {
                                memAddresses[i] = addressValue;
                            } else {
                                memAddresses[i] = -1;
                                System.out.println("O " + i + "º endereço de memória é inválido e será marcado como '-1'.");
                            }
                        }

                        // Cria o objeto `MemoryInput` e processa o mapeamento
                        MemoryInput memory = new MemoryInput(memorySize, memoryInfo[0], memoryInfo[1], memoryInfo[2], memAddresses);
                        System.out.println(MappingType.checkMapping(memory));
                    } else {
                        System.out.println("A quantidade de endereços excede o limite de 2048. Finalizando leitura...");
                    }
                } else {
                    System.out.println("Nenhum endereço válido foi informado.");
                }
            } else {
                System.out.println("Os dados no arquivo não atendem aos requisitos para estruturar uma memória!");
            }
        }
    }

    /**
     * Remove linhas vazias de um arquivo e cria uma cópia no diretório `results`.
     *
     * @param file O caminho do arquivo original.
     * @return O caminho do arquivo processado sem linhas vazias.
     */
    public static String removeEmptyLines(String file) {
        Path originalFilePath = Paths.get(file);
        String resultDir = "results"; //MUDAR DIRETORIO RESULTADO AQUI

        // Cria o caminho para o novo arquivo no diretório `results`
        String fileName = originalFilePath.getFileName().toString();
        String newFilePath = Paths.get(resultDir, fileName + "-result.txt").toString();

        // Leitores e escritores para remover as linhas vazias
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFilePath.toString()));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // Ignora linhas vazias
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFilePath;
    }
}
