package jp.co.hrog.values;

import java.time.LocalDate;

/**
 * 1日の総売上クラス
 */
public class TotalDailySales {
    /** 販売日 */
    private final LocalDate saleDate;
    /** 売上金額 */
    private final int salesAmount;

    /**
     * コンストラクタ
     *
     * @param saleDate    販売日
     * @param salesAmount 売上金額
     */
    public TotalDailySales(final LocalDate saleDate, final int salesAmount) {
        this.saleDate = saleDate;
        this.salesAmount = salesAmount;
    }

    /**
     * 販売日のゲッター
     *
     * @return 販売日
     */
    public LocalDate getSaleDate() {
        return this.saleDate;
    }

    /**
     * 売上金額のゲッター
     *
     * @return 売上金額
     */
    public int getSalesAmount() {
        return this.salesAmount;
    }

    /**
     * 現在の売上金額に引数で与えられた売上金額を加算する。
     *
     * @param salesAmount 売上金額
     * @return 引数の金額加算後の1日の総売上インスタンス
     */
    public TotalDailySales add(final int salesAmount) {
        return new TotalDailySales(this.saleDate, this.salesAmount + salesAmount);
    }
}
