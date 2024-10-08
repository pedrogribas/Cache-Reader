import java.util.ArrayList;

/**
 * Classe responsável por verificar o mapeamento de memória cache.
 */
public class MappingType {

    /**
     * Verifica o tipo de mapeamento da memória cache e calcula os bits de tag, cache hits e cache misses.
     *
     * @param memory O objeto `MemoryInput` contendo as informações de memória.
     * @return Uma string com os resultados do mapeamento: tamanho do bloco, tamanhos de conjunto, bits de tag, cache misses e cache hits.
     */
    public static String checkMapping(MemoryInput memory) {

        // Mapeamento direto
        if (memory.getSets() == memory.getLines()) {
            int lineBits = memory.getBit(memory.getLines());
            memory.tagBits = memory.getMemBitSize() - lineBits - memory.getBlockBitSize();
        }
        // Mapeamento associativo
        else if (memory.getSets() < memory.getLines() && memory.getLines() != memory.getLanes())
        memory.tagBits = memory.getMemBitSize() - memory.getBlockBitSize() - memory.setSizes;


        // Completamente associativo
        else if (memory.getSets() == 1)
        memory.tagBits = memory.getMemBitSize() - memory.blockBitSize;

        ArrayList<Integer> cacheSetList = new ArrayList<>();

        // Processa os endereços de memória
        for (int address : memory.getAddress()) {
            int addressSet = address / memory.getLineBytes();
            if (cacheSetList.contains(addressSet))
                memory.cacheHit++;
            else {
                cacheSetList.add(addressSet);
                memory.cacheMiss++;
            }
        }

        // Retorna os resultados do mapeamento
        return "" + memory.blockBitSize + "\n" + memory.setSizes + "\n" + memory.tagBits + "\n" + memory.cacheMiss + "\n" + memory.cacheHit;
    }
}
