package jp.co.hrog.services;

import jp.co.hrog.enums.Path;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * CSVインポートに関するクラス
 */
public class ImportCsvService {
    /**
     * 指定されたCSVファイルのパーサーを取得する。
     *
     * @param filePath CSVファイルのパス
     * @return CSVパーサー
     */
    public CSVParser getCsvParser(final Path filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath.getPath());
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        CSVFormat format = CSVFormat.Builder.create().setHeader().build();

        return new CSVParser(br, format);
    }
}
