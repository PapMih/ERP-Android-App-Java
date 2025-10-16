package projecterp.Models;

// Lightweight wrapper so Purchases and Outcomes can share one RecyclerView row.
//`totalCostCents` is stored in cents to avoid floating-point errors.

public class OutcomeRow {
    private final String  date;            // yyyy-MM-dd
    private final String  title;           // product category OR outcome type
    private final String  extraInfo;       // size/colour for Purchases, else ""
    private final int     amount;          // 0 for Outcomes
    private final int     totalCostCents;  // cost in cents
    private final boolean purchase;        // true = Purchase, false = Outcome

    public OutcomeRow(String date, String title, String extraInfo,
                      int amount, int totalCostCents, boolean purchase) {
        this.date            = date;
        this.title           = title;
        this.extraInfo       = extraInfo;
        this.amount          = amount;
        this.totalCostCents  = totalCostCents;
        this.purchase        = purchase;
    }

    public String  getDate()           { return date; }
    public String  getTitle()          { return title; }
    public String  getExtraInfo()      { return extraInfo; }
    public int     getAmount()         { return amount; }
    public int     getTotalCostCents() { return totalCostCents; }
    public boolean isPurchase()        { return purchase; }
}
