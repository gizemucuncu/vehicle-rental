package exception;

public class ExceptionMessagesConstants {

    private ExceptionMessagesConstants(){

    }

    public final static String CUSTOMER_EMAIL_ALREADY_EXISTS = "Müşteri emaili zaten kayıtlı.";
    public static final String CUSTOMER_EMAIL_DOES_NOT_EXISTS = "Girilen email için kayıt bulunamadı";
    public static final String CUSTOMER_PASSWORD_DOES_NOT_MATCH = "Girilen şifre veya email bilgisi yanlış";

    public static final String USER_EMAIL_DOES_NOT_EXIST = "Girilen email ile bir kullanıcı bulunmamaktadır!";
    public static final String USER_EMAIL_ALREADY_EXIST = "Girilen emaili zaten kayıtlı!";
    public static final String USER_PASSWORDS_DOES_NOT_MATCH = "Girilen şifre ve ya kullanıcı bilgisi yanlış!";
    public static final String USER_IS_NOT_ADMIN = "Giriş yapan kullanıcı ADMIN rolüne sahip değildir!";
    public static final String USER_IS_NOT_ACTIVE = "Kullanıcı aktif değil ya da bulunamadı!";


    public static final String CATEGORY_NOT_FOUND = "Kategori bulunamadı!";


    public static final String PRODUCT_STOCK_IS_NOT_VALID = "İstenilen ürünün yeterli stok adeti bulunmamaktadır!";

    public static final String CART_ITEMS_EMPTY = "Sepetiniz boş!";

}
