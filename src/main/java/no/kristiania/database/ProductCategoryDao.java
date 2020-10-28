package no.kristiania.database;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductCategoryDao extends AbstractDao<ProductCategory> {

    public ProductCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    public void insert(ProductCategory category) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO product_categories (name) values (?)",
                    Statement.RETURN_GENERATED_KEYS
            )) {
                statement.setString(1, category.getName());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    generatedKeys.next();
                    category.setId(generatedKeys.getInt("id"));
                }
            }
        }
    }

    public ProductCategory retrieve(Integer id) throws SQLException {
        return retrieve(id, "SELECT * FROM product_categories WHERE id = ?");
    }

    public List<ProductCategory> list() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM product_categories")) {
                try (ResultSet rs = statement.executeQuery()) {
                    List<ProductCategory> products = new ArrayList<>();
                    while (rs.next()) {
                        products.add(mapRow(rs));
                    }
                    return products;
                }
            }
        }
    }

    @Override
    protected ProductCategory mapRow(ResultSet rs) throws SQLException {
        ProductCategory category = new ProductCategory();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        return category;
    }
}
