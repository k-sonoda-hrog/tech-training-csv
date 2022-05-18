package jp.co.hrog.services;

import jp.co.hrog.values.TotalDailySales;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static jp.co.hrog.enums.Header.*;
import static jp.co.hrog.enums.Path.SALES_ITEM_CSV;
import static jp.co.hrog.enums.Path.SALES_LIST_CSV;

/**
 * 日々の売上を集計するクラス
 */
public class CalcDailySalesService {
    /** CSVインポートサービス */
    private final ImportCsvService importCsvService;
    /** CSVエクスポートサービス */
    private final ExportCsvService exportCsvService;
    /** 日付のフォーマット形式 */
    private final DateTimeFormatter dtf;
    /** 文字列"start" */
    private static final String START = "start";
    /** 文字列"end" */
    private static final String END = "end";

    /**
     * コンストラクタ
     */
    public CalcDailySalesService() {
        this.importCsvService = new ImportCsvService();
        this.exportCsvService = new ExportCsvService();
        this.dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    }

    /**
     * 日ごとの売上総額を計算し、計算結果をCSVに出力する。
     */
    public void run() {
        exportCsvService.exportHeader();

        Map<String, String> dateRange = getDateRange();
        LocalDate startDate = LocalDate.parse(dateRange.get(START), dtf);
        LocalDate endDate = LocalDate.parse(dateRange.get(END), dtf);
        long count = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i <= count; i++) {
            TotalDailySales totalDailySales = calcTotalDailySales(startDate.plusDays(i));
            exportCsvService.exportData(totalDailySales);
        }
    }

    /**
     * 集計の開始日と終了日を取得する。
     *
     * @return 集計の開始日と終了日が格納されたMap
     */
    private Map<String, String> getDateRange() {
        Map<String, String> dateRange = new HashMap<>();

        try (CSVParser salesListParser = importCsvService.getCsvParser(SALES_LIST_CSV)) {
            Iterator<CSVRecord> salesItemRecords = salesListParser.iterator();
            dateRange.put(START, salesItemRecords.next().get(SALE_DATE.getHeader()));

            while (salesItemRecords.hasNext()) {
                CSVRecord salesItemRecord = salesItemRecords.next();
                if (!salesItemRecords.hasNext()) {
                    dateRange.put(END, salesItemRecord.get(SALE_DATE.getHeader()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateRange;
    }

    /**
     * 1日の売上総額を計算して返す。
     *
     * @param targetDate 計算対象の日付
     * @return 1日の総売上インスタンス
     */
    private TotalDailySales calcTotalDailySales(final LocalDate targetDate) {
        TotalDailySales totalDailySales = new TotalDailySales(targetDate, 0);

        try (CSVParser salesListParser = importCsvService.getCsvParser(SALES_LIST_CSV)) {
            for (CSVRecord salesListRecord : salesListParser) {
                LocalDate saleDate = LocalDate.parse(salesListRecord.get(SALE_DATE.getHeader()), dtf);
                if (saleDate.isEqual(targetDate)) {
                    String productCode = salesListRecord.get(PRODUCT_CODE.getHeader());
                    int unitPrice = getUnitPrice(productCode);
                    int quantity = Integer.parseInt(salesListRecord.get(QUANTITY.getHeader()));
                    totalDailySales = totalDailySales.add(unitPrice * quantity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalDailySales;
    }

    /**
     * 商品の単価を取得する。
     *
     * @param productCode 商品コード
     * @return 商品の単価
     */
    private int getUnitPrice(final String productCode) {
        int unitPrice = 0;

        try (CSVParser salesItemParser = importCsvService.getCsvParser(SALES_ITEM_CSV)) {
            for (CSVRecord salesItemRecord : salesItemParser) {
                if (salesItemRecord.get(PRODUCT_CODE.getHeader()).equals(productCode)) {
                    unitPrice = Integer.parseInt(salesItemRecord.get(UNIT_PRICE.getHeader()));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unitPrice;
    }
}
