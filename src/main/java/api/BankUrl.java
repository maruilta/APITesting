package api;

public enum BankUrl {
    URL("https://60f58a6818254c00176dff1b.mockapi.io/api/academy/users/");
    private final String url;
    BankUrl(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}