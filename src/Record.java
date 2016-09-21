import java.text.DecimalFormat;

/**
 * Created by watariMac on 2016/08/03.
 */
public class Record {
    static double k = 50;//執行価格
    static double interresetRate = 0.05; //金利
    static double T_t = 20.0 / 52.0; //オプション期間(年)
    static double stock = 100000; //初期購入枚数
    double stockPrice;
    double d1;
    double d2;
    double delta;
    double buyStock; //購入株式数
    double buyCost;//購入コスト
    double outstanding;//累積コスト(かしいれざんだか)
    double interresetCost; //金利コスト
    double callOption;
    double callOptionValue;

    Record(double sp, double sigma) {
        stockPrice = sp;
        d1 = (Math.log(stockPrice / k) + (interresetRate + 1 / 2.0 * sigma * sigma) * T_t) / (sigma * Math.sqrt(T_t));
        delta = calsGaussianFunction(d1);
        buyStock = delta * stock;
        buyCost = stockPrice * buyStock;
        outstanding = buyCost;
        interresetCost = outstanding * interresetRate * 1 / 52.0;
        d2 = d1 - sigma * Math.sqrt(T_t);
        callOption = stockPrice * delta - k * Math.exp(-interresetCost * T_t) * calsGaussianFunction(d2);
        callOptionValue = callOption * stock;
    }

    Record(Record r, double rate, double sigma) {
        stockPrice = r.stockPrice * (rate * sigma * 0.2 + 0.01) + r.stockPrice;
        d1 = (Math.log(stockPrice / k) + (interresetRate + 1 / 2.0 * sigma * sigma) * T_t) / (sigma * Math.sqrt(T_t));
        delta = calsGaussianFunction(d1);
        buyStock = (delta - r.delta) * stock;
        buyCost = stockPrice * buyStock;
        outstanding = r.outstanding + buyCost + r.interresetCost;
        interresetCost = outstanding * interresetRate * 1 / 52.0;
        d2 = d1 - sigma * Math.sqrt(T_t);
        callOption = stockPrice * delta - k * Math.exp(-interresetCost * T_t) * calsGaussianFunction(d2);
        callOptionValue = callOption * stock;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        DecimalFormat df2 = new DecimalFormat("##,##0.0");

        return String.format("%7s ",df.format(stockPrice))
                + String.format("%7s ",df.format(delta))
                + String.format("%10s ",df2.format(buyStock))
                + String.format("%10s ",df2.format(buyCost / 1000))
                + String.format("%10s ",df2.format(outstanding / 1000))
                + String.format("%10s ",df.format(interresetCost / 1000))
                + String.format("%20s ",df2.format(callOptionValue));

    }

    //標準正規分布の確率密度
    public static double calsGaussianFunction(double x) {
        double n = 0;
        for (double i = -10.0; i < x; i = i + 0.00001) {
            n = n + Math.exp(-0.5 * i * i) / Math.sqrt(2.0 * Math.PI) * 0.00001;
        }
        return n;
    }

}
