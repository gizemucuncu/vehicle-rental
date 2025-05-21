import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.Category;
import model.Customer;
import model.User;
import model.Vehicle;
import model.enums.CustomerType;
import model.enums.RentalType;
import model.enums.Role;
import service.*;

import javax.xml.transform.Source;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class VehicleRentalMain {

    private static User LOGINED_USER;
    private static Customer LOGINED_CUSTOMER;

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final CategoryService categoryService = new CategoryService();
    private static final VehicleService vehicleService = new VehicleService();


    public static void main(String[] args) {
        while (true) {

            getMainMenu();

            String choise = scanner.nextLine();

            try {
                switch (choise) {
                    case "1":
                        getUserMenu();
                        break;
                    case "2":
                        getCustomerMenu();
                        break;
                    case "0":
                        System.out.println("Çıkış yapılıyor ...");
                        return;
                    default:
                        System.out.println("Geçersiz Seçim");
                }
            } catch (VehicleRentalException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void getCustomerMenu() throws VehicleRentalException {
        while (true) {
            System.out.println("=== MÜŞTERİ GİRİŞ PANELİ ===");
            System.out.println("1 - Müşteri Kayıt Ol");
            System.out.println("2 - Müşteri Giriş Yap");
            System.out.println("0 - Geri Dön");
            System.out.print("Seçim Yapınız: ");

            String choise = scanner.nextLine();

            switch (choise) {
                case "1":
                    registerCustomer();
                    break;
                case "2":
                    loginCustomer();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Geçersiz Seçim");
            }
        }
    }

    private static void registerCustomer() throws VehicleRentalException {

        System.out.print("İsim: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();
        System.out.print("Müşteri Tipini Giriniz (INDIVIDUAL / CORPORATE): ");
        String customerTypeString = scanner.nextLine().toUpperCase(Locale.ENGLISH);
        CustomerType customerType = CustomerType.valueOf(customerTypeString);
        System.out.print("Doğum Tarihi Bilgisi Giriniz (gg.aa.yyyy): ");
        String birthDateStr = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);
        System.out.print("Yaş Bilgisi Giriniz: ");
        int age = scanner.nextInt();

        CustomerService customerService = new CustomerService();
        customerService.save(name, email, password, customerType, age, birthDate);
    }

    private static void getUserMenu() throws VehicleRentalException {
        while (true) {
            System.out.println("=== KULLANICI GİRİŞ PANELİ ===");
            System.out.println("1 - Kullanıcı Kayıt Ol");
            System.out.println("2 - Kullanıcı Giriş Yap");
            System.out.println("0 - Geri Dön");
            System.out.print("Seçim Yapınız: ");

            String choise = scanner.nextLine();

            switch (choise) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Geçersiz Seçim");
            }
        }
    }

    private static void registerUser() throws VehicleRentalException {
        System.out.print("Kullanıcı Adı: ");
        String userName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();
        System.out.print("Rol Seçiniz: (ADMIN)");
        String roleString = scanner.nextLine().toUpperCase();

        Role role = Role.valueOf(roleString);
        userService.save(userName, password, role, email);
    }

    private static void loginUser() throws VehicleRentalException {
        System.out.print("Kullanıcı Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        User loginedUser = userService.login(email, password);

        if (loginedUser != null && loginedUser.getActive()) {
            LOGINED_USER = loginedUser;
            getLoginedUserMenu();
        } else {
            throw new VehicleRentalException(ExceptionMessagesConstants.USER_IS_NOT_ACTIVE);
        }
    }

    private static void getLoginedUserMenu() throws VehicleRentalException {
        while (true) {
            {
                System.out.println("=== LOGIN OLAN KULLANICI MENUSÜ ===");
                System.out.println("1 - Kategori Oluştur");
                System.out.println("2 - Kategori Listele");
                System.out.println("3 - Kategori Sil");
                System.out.println("4 - Ürün Oluştur");
                System.out.println("5 - Ürün Listele");
                System.out.println("6 - Ürün Sil");
                System.out.println("7 - Ürün Arama");
                System.out.println("8 - Ürün Filtereleme(Kategori Bazlı)");
                System.out.println("9 - Kiralanmış Araçları Listele");
                System.out.println("0 - Geri");
                System.out.print("Seçim Yapınız: ");
                String choise = scanner.nextLine();

                switch (choise) {
                    case "1":
                        createCategory(); // TODO kayıt yapılmıyor ?
                        break;
                    case "2":
                        categoryList();
                        break;
                    case "3":
                        categoryDelete();
                        break;
                    case "4":
                        vehicleCreate();
                        break;
                    case "5":
                        vehicleList();
                        break;
                    case "6":
                        vehicleDelete();
                        break;
                    case "7":
                        vehicleSearch();
                        break;
                    case "8":
                        vehicleFiltering();
                        break;
                    case "9":
                        rentalVehicleList();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Geçersiz Seçim");

                }
            }
        }
    }

    private static void rentalVehicleList() {
        /*List<Order> orders = orderService.getAllByCustomer(LOGINED_CUSTOMER);

        System.out.println("--------------Siparişlerim----------------");

        for (Order order : orders) {

            System.out.printf("Sipariş #%d - %s\n",
                    order.getId(), order.getOrderDate());

            for (OrderItem item : order.getOrderItems()) {

                System.out.printf("  -> %s - %d - %s\n",
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice());
            }
        }


        System.out.println("--------------------------------------------");

         */
    }

    private static void vehicleFiltering() {
        System.out.print("Kategori ismi giriniz: ");
        String categoryName = scanner.nextLine();

        List<Vehicle> vehicles = vehicleService.getAllByCategoryName(categoryName);

        System.out.println("\n<====> ARAÇ LİSTESİ (Filtereme Sonucu) <====>");

        vehicles.forEach(vehicle ->
                System.out.printf("Araç: %s - Ücret: %s - Kategori: %s\n", vehicle.getName(), vehicle.getRentPrice(), vehicle.getCategory().getName())
        );

        System.out.println("<=======================>");
    }

    private static void vehicleSearch() {

        System.out.print("Aramak istediğiniz araç ismi giriniz: ");
        String searchVehicleName = scanner.nextLine();

        List<Vehicle> vehicles = vehicleService.search(searchVehicleName);

        System.out.println("\n<====> ARAÇ LİSTESİ (Arama Sonucu) <====>");

        vehicles.forEach(vehicle ->
                System.out.printf("Araç: %s - Ücret: %s - Kategori: %s\n", vehicle.getName(), vehicle.getRentPrice(), vehicle.getCategory().getName())
        );

        System.out.println("<=======================>");
    }

    private static void vehicleDelete() {
        System.out.print("Silinecek araç id'sini giriniz: ");
        String vehicleId = scanner.nextLine();
        vehicleService.deleteById(Long.parseLong(vehicleId));
    }

    private static void vehicleList() {
        int totalPage = vehicleService.getTotalPage();
        int page = 1;

        do {
            List<Vehicle> vehicles = vehicleService.getAll(page);
            System.out.println("\n==== ÜRÜN LİSTESİ(Sayfa )" + page + "/" + totalPage + "====");
            vehicles.forEach(vehicle -> System.out.printf("%s - %s - %s\n", vehicle.getName(), vehicle.getRentPrice(), vehicle.getCategory().getName()));
            System.out.println("======");

            System.out.print("Sonraki sayfa sayısı: ");
            String pageStr = scanner.nextLine();
            page = Integer.parseInt(pageStr);

        } while (page <= totalPage);
    }

    private static void vehicleCreate() throws VehicleRentalException {
        System.out.println("Araç ismi girin: ");
        String vehicleName = scanner.nextLine();
        System.out.println("Araç kiralama fiyatı girin: ");
        String vehicleRentPrice = scanner.nextLine();
        System.out.print("Araç stok bilgisini giriniz: ");
        String stock = scanner.nextLine();
        System.out.print("Kategori id giriniz: ");
        String categoryId = scanner.nextLine();

        Category category = categoryService.getById(Long.parseLong(categoryId));
        Vehicle vehicle = new Vehicle(vehicleName, new BigDecimal(vehicleRentPrice), Integer.parseInt(stock), category);

        vehicleService.save(vehicle, LOGINED_USER);

    }

    private static void categoryDelete() {
        System.out.println("Silinmek istenen Kategronin id bilgisi: ");
        String categoryId = scanner.nextLine();

        categoryService.deleteById(Long.parseLong(categoryId));
    }

    private static void categoryList() {
        List<Category> categoryList = categoryService.getAll();
        categoryList.forEach(System.out::println);
    }

    private static void createCategory() throws VehicleRentalException {
        throw new VehicleRentalException("Kategori Yaratılamadı"); // TODO Böyle mi olmalı bu ?
    }

    private static void getMainMenu() {
        System.out.println("=== GİRİŞ TÜRÜ SEÇİN ===");
        System.out.println("1 - Kullanıcı Girişi (ADMIN)");
        System.out.println("2 - Müşteri Girişi");
        System.out.println("0 - Çıkış");
        System.out.print("Seçim Yapınız: ");
    }

    // TODO SİLİNECEK BU
   /* TODO Silinecek sanırım
    private static void saveCustomer(Scanner scanner) throws VehicleRentalException {
        System.out.print("İsim: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        CustomerService customerService = new CustomerService();
        customerService.save(name, email, password);
    }

    */


    private static void loginCustomer() throws VehicleRentalException {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        CustomerService customerService = new CustomerService();
        LOGINED_CUSTOMER = customerService.login(email, password);

        while (true) {
            System.out.println("1 - Araç Listele");
            System.out.println("2 - Araç Arama");
            System.out.println("3 - Araç Filtereleme(Kategori Bazlı)");
            System.out.println("4 - Araç Kirla ");
            System.out.println("5 - Kiralamları Görüntüle");
            System.out.println("0 - Geri");
            System.out.print("Seçim Yapınız: ");
            String choise = scanner.nextLine();

            switch (choise) {
                case "1":
                    vehicleList();
                    break;
                case "2":
                    vehicleSearch();
                    break;
                case "3":
                    vehicleFiltering();
                    break;
                case "4":
                    vehicleRent();
                    break;
                /*case "5":
                    showRentalHistory();
                    break;

                 */
                case "0":
                    return;
                default:
                    System.out.println("Geçersiz Seçim");
            }
        }
    }

    private static void vehicleRent() throws VehicleRentalException {
        System.out.print("Araç ismini giriniz: ");
        String vehicleName = scanner.nextLine();

        Vehicle vehicle = vehicleService.getByName(vehicleName);
        System.out.print("Kiralama Tipi (HOURLY, DAILY, WEEKLY, MONTHLY): ");
        RentalType rentalType = RentalType.valueOf(scanner.nextLine().toUpperCase(Locale.ENGLISH));

        if (vehicle == null) {
            System.out.println("Araç bulunamadı");
        } else {
            System.out.print("Başlangıç Tarihini (yyyy-MM-ddTHH:mm) Formatı ile Giriniz: ");
            LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine());

            System.out.print("Bitiş Tarihini (yyyy-MM-ddTHH:mm) Formatı ile Giriniz: ");
            LocalDateTime endDate = LocalDateTime.parse(scanner.nextLine());

            RentalService rentalService = new RentalService();
            rentalService.rent(LOGINED_CUSTOMER, vehicle, rentalType, startDate, endDate);
        }
    }
/*
Seçim Yapınız: 4
Araç ismini giriniz: Z-Motosiklet
Kiralama Tipi (HOURLY, DAILY, WEEKLY, MONTHLY): Daily
Başlangıç Tarihini (yyyy-MM-ddTHH:mm) Formatı ile Giriniz: 2025-05-21T10:00
Bitiş Tarihini (yyyy-MM-ddTHH:mm) Formatı ile Giriniz: 2025-05-23T10:00
Kiralama tamamlandı: ₺1400.00 | Depozito: ₺210000.000
 */
}

// TODO kullanıcı tipi ekle +  AGE KALDIR Birth_Date alanı ekledik


