package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.ProductDao;
import model.entities.Product;

public class ProductDaoJDBC implements ProductDao{

	private Connection conn;

	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public Product findById(Integer Id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM product "
					+ "WHERE Id = ?");
			
			st.setInt(1, Id);
			rs = st.executeQuery();
			if (rs.next()) {
				Product obj = instantiateProduct(rs);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Product> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM product ORDER BY NAME");
			
			rs = st.executeQuery();
			List<Product> list = new ArrayList<>();
			
			while (rs.next()) {
				Product obj = instantiateProduct(rs);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public void insert(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO product "
					+ "(Name, Price) "
					+ "VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getPrice());
			
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows Affected");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE product "
					+ "SET Name = ?, Price = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setDouble(2, obj.getPrice());
			st.setInt(3, obj.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM product "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}
	
	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product obj = new Product();
		obj.setId(rs.getInt("Id"));
		obj.setPrice(rs.getDouble("Price"));
		obj.setName(rs.getString("Name"));
		return obj;
	}
}
