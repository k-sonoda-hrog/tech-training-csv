package jp.co.hrog.enums;

/**
 * 各種ヘッダ定義用のEnum
 */
public enum Header {
    /** 文字列"販売日" */
    SALE_DATE("販売日"),
    /** 文字列"商品コード" */
    PRODUCT_CODE("商品コード"),
    /** 文字列"数量" */
    QUANTITY("数量"),
    /** 文字列"単価" */
    UNIT_PRICE("単価"),
    /** 文字列"売上総額" */
    TOTAL_SALES("売上総額");

    /** 各種ヘッダ */
    private final String header;

    /**
     * コンストラクタ
     *
     * @param header 各種ヘッダ
     */
    Header(final String header) {
        this.header = header;
    }

    /**
     * 各種ヘッダのゲッター
     *
     * @return 各種ヘッダ
     */
    public String getHeader() {
        return this.header;
    }
}
