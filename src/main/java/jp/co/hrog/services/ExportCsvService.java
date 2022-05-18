package jp.co.hrog.services;

import jp.co.hrog.enums.Path;
import jp.co.hrog.values.TotalDailySales;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static jp.co.hrog.enums.Header.SALE_DATE;
import static jp.co.hrog.enums.Header.TOTAL_SALES;

/**
 * CSVエクスポートに関するクラス
 */
public class ExportCsvService {
    /** 文字列"円" */
    private static final String YEN = "円";

    /**
     * ヘッダ部のエクスポート処理を行う。
     */
    public void exportHeader() {
        try (CSVPrinter dailySalesPrinter = getCsvPrinter()) {
            dailySalesPrinter.printRecord(SALE_DATE.getHeader(), TOTAL_SALES.getHeader());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * データ部のエクスポート処理を行う。
     */
    public void exportData(final TotalDailySales totalDailySales) {
        try (CSVPrinter dailySalesPrinter = getCsvPrinter()) {
            LocalDate saleDate = totalDailySales.getSaleDate();
            String salesAmount = totalDailySales.getSalesAmount() + YEN;
            dailySalesPrinter.printRecord(saleDate, salesAmount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 出力先CSVファイルのプリンターを取得する。
     *
     * @return CSVプリンター
     */
    private CSVPrinter getCsvPrinter() throws IOException {
        FileOutputStream fos = new FileOutputStream(Path.DAILY_SALES_CSV.getPath(), true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        CSVFormat format = CSVFormat.Builder.create().build();

        return new CSVPrinter(bw, format);
    }
}
