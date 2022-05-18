package jp.co.hrog.enums;

/**
 * 各種パス定義用のEnum
 */
public enum Path {
    /** CSVリソース保存先のディレクトリパス */
    CSV_DIR("src\\main\\resources\\csv\\"),
    /** SalesItem.csvのファイルパス */
    SALES_ITEM_CSV(CSV_DIR.getPath() + "SalesItem.csv"),
    /** SalesList.csvのファイルパス */
    SALES_LIST_CSV(CSV_DIR.getPath() + "SalesList.csv"),
    /** 処理結果出力先のファイルパス */
    DAILY_SALES_CSV(CSV_DIR.getPath() + "DailySales.csv");

    /** 各種パス */
    private final String path;

    /**
     * コンストラクタ
     *
     * @param path 各種パス
     */
    Path(final String path) {
        this.path = path;
    }

    /**
     * 各種パスのゲッター
     *
     * @return 各種パス
     */
    public String getPath() {
        return this.path;
    }
}