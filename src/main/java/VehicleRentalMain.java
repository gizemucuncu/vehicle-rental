import exception.ExceptionMessagesConstants;
import exception.VehicleRentalException;
import model.*;
import model.enums.CustomerType;
import model.enums.RentalType;
import model.enums.Role;
import service.*;

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
    private static final RentalService rentalService = new RentalService();

    public static void main(String[] args) {
        System.out.println("SİSTEM BAŞLADI");
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

        CustomerService customerService = new CustomerService();
        customerService.save(name, email, password, customerType, birthDate);
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
                System.out.println("4 - Araç Ekle");
                System.out.println("5 - Araç Listele");
                System.out.println("6 - Araç Sil");
                System.out.println("7 - Araç Arama");
                System.out.println("8 - Araç Filtereleme(Kategori Bazlı)");
                System.out.println("9 - Kiralanmış Araçları Listele");
                System.out.println("0 - Geri");
                System.out.print("Seçim Yapınız: ");
                String choise = scanner.nextLine();

                switch (choise) {
                    case "1":
                        createCategory();
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
        RentalService rentalService = new RentalService();
        List<Rental> rentals = rentalService.getAllRentals();

        if (rentals.isEmpty()) {
            System.out.println("Kayıtlı kiralama yok.");
        } else {
            System.out.println("<====> TÜM ARAÇ KİRALAMA GEÇMİŞİ <====>");
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
        }
    }

    private static void vehicleFiltering() {
        System.out.print("Kategori ismi giriniz: ");
        String categoryName = scanner.nextLine();

        List<Vehicle> vehicles = vehicleService.getAllByCategoryName(categoryName);

        System.out.println("\n<====> ARAÇ LİSTESİ (Filtereme Sonucu) <====>");

        vehicles.forEach(vehicle ->
                System.out.printf("Araç: %s - Ücret: %s - Kategori: %s\n", vehicle.getName(), vehicle.getPrice(), vehicle.getCategory().getName())
        );

        System.out.println("<=======================>");
    }

    private static void vehicleSearch() {

        System.out.print("Aramak istediğiniz araç ismi giriniz: ");
        String searchVehicleName = scanner.nextLine();

        List<Vehicle> vehicles = vehicleService.search(searchVehicleName);

        System.out.println("\n<====> ARAÇ LİSTESİ (Arama Sonucu) <====>");

        vehicles.forEach(vehicle ->
                System.out.printf("Araç: %s - Ücret: %s - Kategori: %s\n", vehicle.getName(), vehicle.getPrice(), vehicle.getCategory().getName())
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
            System.out.println("\n<====> ÜRÜN LİSTESİ(Sayfa )" + page + "/" + totalPage + "<====>");
            vehicles.forEach(vehicle -> System.out.printf("%s - %s - %s\n", vehicle.getName(), vehicle.getPrice(), vehicle.getCategory().getName()));
            System.out.println("<===============>");

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
        System.out.println("Kategori ismi girin: ");
        String categoryName = scanner.nextLine();
        System.out.println("Araç kiralama fiyatı girin (Saatlik): ");
        BigDecimal perHour = scanner.nextBigDecimal();
        System.out.println("Araç kiralama fiyatı girin (Günlük): ");
        BigDecimal perDay = scanner.nextBigDecimal();
        System.out.println("Araç kiralama fiyatı girin (Haftalık): ");
        BigDecimal perWeek = scanner.nextBigDecimal();
        System.out.println("Araç kiralama fiyatı girin (Aylık): ");
        BigDecimal perMonth = scanner.nextBigDecimal();

        Category category = new Category(categoryName, perHour, perDay, perWeek, perMonth);
        categoryService.save(category, LOGINED_USER);
    }

    private static void getMainMenu() {
        System.out.println("<<<<<< GİRİŞ TÜRÜ SEÇİN >>>>>>");
        System.out.println("1 - Kullanıcı Girişi (ADMIN)");
        System.out.println("2 - Müşteri Girişi");
        System.out.println("0 - Çıkış");
        System.out.print("Seçim Yapınız: ");
    }

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
            System.out.println("3 - Araç Filtereleme (Kategori Bazlı)");
            System.out.println("4 - Araç Kirala ");
            System.out.println("5 - Kiralama Geçmişini Görüntüle");
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
                case "5":
                    showRentalHistory();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Geçersiz Seçim");
            }
        }
    }

    private static void showRentalHistory() {
        System.out.println("Kiralama Geçmişiniz: ");
        rentalService.showRentHistory(LOGINED_CUSTOMER);

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
}



