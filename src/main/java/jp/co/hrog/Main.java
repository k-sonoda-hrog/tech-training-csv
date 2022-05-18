package jp.co.hrog;

import jp.co.hrog.services.CalcDailySalesService;

/**
 * メイン処理を行うクラス
 */
public class Main {
    /**
     * mainメソッド
     *
     * @param args 未使用
     */
    public static void main(String[] args) {
        CalcDailySalesService CalcDailySalesService = new CalcDailySalesService();
        CalcDailySalesService.run();
    }
}
