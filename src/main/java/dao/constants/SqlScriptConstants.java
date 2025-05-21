package dao.constants;

public class SqlScriptConstants {


    public static final String USER_FIND_BY_EMAIL = """
            SELECT * FROM USERS WHERE email = ?
            """;

    public static final String USER_SAVE = """
            INSERT INTO users (username, password, role, email, active)
            VALUES (?,?,?,?,?)
            """;

    public static final String CUSTOMER_SAVE = """
            INSERT INTO customer (name,email, password, customer_type, birth_date) VALUES (?,?,?,?,?)
            """;

    public static final String CUSTOMER_FIND_BY_ID = """
            SELECT * FROM customer WHERE id = ?
            """;

    public static final String CUSTOMER_FIND_ALL = """
            SELECT * FROM customer
            """;

    public static final String CUSTOMER_EXIST_BY_EMAIL = """
            SELECT * FROM customer WHERE email = ?
            """;
    public static final String CATEGORY_FIND_ALL = """
            SELECT * FROM category
            """;
    public static final String CATEGORY_DELETE = """
            DELETE FROM category WHERE id = ? 
            """;
    public static final String CATEGORY_FIND_BY_ID = """
            SELECT * FROM category WHERE id = ?
            """;
    public static final String VEHICLE_SAVE = """
            INSERT INTO vehicle (name, price, stock, category_id, created_by, updated_by)
            VALUES (?,?,?,?,?,?)
            """;
    public static final String VEHICLE_FIND_ALL = """
            SELECT v.id    as id,
                   v.name  as name,
                   v.price as price,
                   v.stock as stock,
                   c.id    as category_id,
                   c.name  as category_name
            FROM vehicle v,
                 category c
            WHERE v.category_id = c.id
            ORDER BY v.id asc
            LIMIT ? OFFSET ?;
            """;
    public static final String VEHICLE_TOTAL_PAGE_COUNT = """
            SELECT COUNT(*) FROM vehicle
            """;
    public static final String VEHICLE_DELETE = """
            DELETE FROM vehicle WHERE id = ?
            """;
    public static final String VEHICLE_SEARCH_BY_NAME = """
             SELECT v.id    as id,
                   v.name  as name,
                   v.price as price,
                   v.stock as stock,
                   c.id    as category_id,
                   c.name  as category_name
            FROM vehicle v
                     LEFT JOIN public.category c on c.id = v.category_id
            WHERE v.name LIKE ?
            """;
    public static final String VEHICLE_FIND_BY_CATEGORY_NAME = """
            select v.id    as id,
                   v.name  as name,
                   v.price as price,
                   v.stock as stock,
                   c.id    as category_id,
                   c.name  as category_name
            from vehicle v
                     join category c on c.id = v.category_id
            where c.name = ?
            """;
    public static final String VEHICLE_FIND_BY_NAME = """
             SELECT v.*,
             c.id AS category_id,
             c.name AS category_name,
             c.rental_rate_per_hour,
             c.rental_rate_per_day,
            c.rental_rate_per_week,
             c.rental_rate_per_month
            FROM vehicle v
             JOIN category c ON v.category_id = c.id
             WHERE v.name = ?
            """;
    public static final String VEHICLE_FIND_TO_RENT_BY_NAME = """
            SELECT v.*,
            c.id AS category_id,
            c.name AS category_name,
            c.rental_rate_per_hour,
            c.rental_rate_per_day,
            c.rental_rate_per_week,
            c.rental_rate_per_month
            FROM vehicle v
            JOIN category c ON v.category_id = c.id
            WHERE v.name = ?
            """;

    public static final String RENTAL_INSERT = """
            INSERT INTO rental (customer_id, vehicle_id, rental_type, start_date, end_date, total_price, deposit) VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    public static final String GET_RENTAL_HISTORY = """
            SELECT r.rental_type,
                      r.start_date,
                      r.end_date,
                      r.total_price,
                      r.deposit,
                      v.name AS vehicle_name,
                      c.name AS category_name,
                      cu.name AS customer_name,
                      cu.email AS email,
                      cu.birth_date AS birth_date,
                      cu.customer_type AS customer_type
               FROM rental r
               JOIN vehicle v ON r.vehicle_id = v.id
               JOIN category c ON v.category_id = c.id
               JOIN customer cu ON r.customer_id = cu.id
               WHERE r.customer_id = ?
               ORDER BY r.start_date DESC;
            """;

    public static final String GET_ALL_RENTAL_HISTORY_TO_ADMIN = """
            SELECT r.rental_type,
                      r.start_date,
                      r.end_date,
                      r.total_price,
                      r.deposit,
                      v.name AS vehicle_name,
                      c.name AS category_name,
                      cu.name AS customer_name,
                      cu.email AS email,
                      cu.birth_date AS birth_date,
                      cu.customer_type AS customer_type
               FROM rental r
               JOIN vehicle v ON r.vehicle_id = v.id
               JOIN category c ON v.category_id = c.id
               JOIN customer cu ON r.customer_id = cu.id
               ORDER BY r.start_date DESC;
            """;
    public static final String CATEGORY_SAVE = """
             INSERT INTO category (
                    name,
                    rental_rate_per_hour,
                    rental_rate_per_day,
                    rental_rate_per_week,
                    rental_rate_per_month
                ) VALUES (?, ?, ?, ?, ?)
            """;
}
