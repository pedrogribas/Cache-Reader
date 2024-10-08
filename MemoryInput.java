/**
 * Classe que contém os dados de entrada para o mapeamento de memória cache.
 */
public class MemoryInput {
    private Double memSize;
    private int words;
    private int lines;
    private int lanes;
    private int[] address;

    int memBitSize;
    int lineBytes;
    int sets;

    int blockBitSize;
    int setSizes;
    int tagBits;
    int cacheMiss;
    int cacheHit;

    /**
     * Construtor da classe MemoryInput.
     *
     * @param memSize Tamanho da memória em palavras.
     * @param words Número de palavras por bloco.
     * @param lines Número de linhas de cache.
     * @param lanes Número de vias.
     * @param address Array de endereços de memória.
     */
    public MemoryInput(Double memSize, int words, int lines, int lanes, int[] address) {
        this.memSize = memSize;
        this.words = words;
        this.lines = lines;
        this.lanes = lanes;
        this.address = address;

        findAllValues();
    }

    /**
     * Calcula todos os valores importantes para o mapeamento de memória cache.
     */
    private void findAllValues() {
        this.memBitSize = getBit((int) (Math.floor(memSize)));
        this.lineBytes = 4 * getWords();
        this.sets = getLines() / getLanes();
        this.blockBitSize = getBit(lineBytes);
        this.setSizes = getBit(sets);
        this.tagBits = 0;
        this.cacheMiss = 0;
        this.cacheHit = 0;
    }

    /**
     * Calcula o número de bits necessários para representar um valor.
     *
     * @param value O valor para o qual calcular os bits.
     * @return O número de bits.
     */
    public int getBit(int value) {
        if (value == 1)
            return 0;

        return (int) (Math.log(value) / Math.log(2));
    }

    /**
     * Converte os endereços de memória em uma string.
     *
     * @return Uma string contendo todos os endereços de memória.
     */
    public String addressToString() {
        String addressString = "";
        for (int i = 0; i < this.address.length; i++)
            addressString += this.address[i] + " ";
        return addressString;
    }

    // Métodos getters
    public int getMemBitSize() {
        return memBitSize;
    }

    public int getLineBytes() {
        return lineBytes;
    }

    public int getSets() {
        return sets;
    }

    public int getBlockBitSize() {
        return blockBitSize;
    }

    public int getSetSizes() {
        return setSizes;
    }

    public int getTagBits() {
        return tagBits;
    }

    public int getCacheMiss() {
        return cacheMiss;
    }

    public int getCacheHit() {
        return cacheHit;
    }

    public Double getMemSize() {
        return memSize;
    }

    public int getWords() {
        return words;
    }

    public int getLines() {
        return lines;
    }

    public int getLanes() {
        return lanes;
    }

    public int[] getAddress() {
        return address;
    }
}
