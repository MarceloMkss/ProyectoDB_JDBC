package com.twtspain.aplicacion.jdbc.ProyectoDB_JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class PruebaJdbc {

	public static Logger log = Logger.getLogger("PruebaJDBC");

	public static void main(String[] args) {

		final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		final String USER = "HR";
		final String PASS = "hr";

		/**
		 * SQL_SELECT para selecionar todos los datos de employees
		 */
		final String SQL_SELECT = "SELECT employee_id, first_name, last_name, hire_date " + "FROM employees";

		/**
		 * SQL_SELECT_ID recupera la id de una region
		 */
		final String SQL_SELECT_ID = "SELECT * FROM REGIONS WHERE REGION_ID = ?";

		/**
		 * SQL_INSERT inserta una nueva region
		 */
		final String SQL_INSERT = "INSERT INTO REGIONS (REGION_ID, REGION_NAME) " + "VALUES (5, 'BRASIL')";

		/**
		 * SQL_UPDATE Edita un dato de la tabla region
		 */
		final String SQL_UPDATE = "UPDATE REGIONS SET REGION_NAME = ?" + "WHERE region_name = ?";

		/**
		 * SQL_DELETE elimina un dato de la tabla regiones
		 */
		final String SQL_DELETE = "DELETE FROM REGIONS WHERE REGION_ID = ?";

		/*
		 * Conección con driver de Oracle
		 */

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			log.info("driver cargado");
		} catch (ClassNotFoundException e) {
			log.info("no se a podido cargar el driver");
			e.printStackTrace();
		}

		// TRY CON RECURSOS
		try {

			Connection conn = DriverManager.getConnection(URL, USER, PASS);
			log.info("coneccion establecida");

			Statement s = conn.createStatement();

			/*
			 * SQL_SELECT seleciona todos los datos de la base de datos
			 */

			ResultSet rs = s.executeQuery(SQL_SELECT);
			System.out.println(SQL_SELECT);

			while (rs.next()) {

				System.out.print(".Id " + rs.getInt(1));
				System.out.print(" - " + rs.getString(2));
				System.out.print(" - " + rs.getString(3));

				java.sql.Date fechaAlta = rs.getDate(4);
				java.time.LocalDate fecha = fechaAlta.toLocalDate();
				System.out.println(" - alta " + fecha);

			}

			/*
			 * SQL_SELECT_ID Seleciona un dato de la Base de Datos por su ID
			 */

			int id = 5;

			PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ID);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			System.out.println(SQL_SELECT_ID);

			while (rs.next()) {
				log.info("Select por id Encontrada");
				System.out.println(rs.getInt("REGION_ID") + ", " + rs.getString("REGION_NAME"));
			}

			/*
			 * SQL_INSERT inserta un nuevo registro en la Base de Datos
			 */

			ps = conn.prepareStatement(SQL_INSERT);

			int numeroRegistrosAfectados = ps.executeUpdate();

			System.out.println(SQL_INSERT);

			System.out.println(numeroRegistrosAfectados);

			/*
			 * Update Edita/Atualiza un dato de la tabla Region
			 */

			String REGION_NAME_ANTIGUO = "OCEANIA";
			String REGION_NAME_NUEVO = "OCEANIA_NUEVA";

			ps = conn.prepareStatement(SQL_UPDATE);

			ps.setString(1, REGION_NAME_NUEVO);
			ps.setString(2, REGION_NAME_ANTIGUO);

			numeroRegistrosAfectados = ps.executeUpdate();

			System.out.println(SQL_UPDATE);
			System.out.println("Región atualizada con exito!!!");

			System.out.println(numeroRegistrosAfectados);

			/*
			 * delete sirve para borrar un dato de la base de datos
			 */

			id = 5;

			ps = conn.prepareStatement(SQL_DELETE);

			ps.setInt(1, id);

			numeroRegistrosAfectados = ps.executeUpdate();

			System.out.println(SQL_DELETE);
			System.out.println("EL DATO HA SIDO BORRADO COM EXITO");

			System.out.println(numeroRegistrosAfectados);

		} catch (SQLException e) {
			log.info("coneccion no establecida");
			e.printStackTrace();
		}

	}// main

}// class PruebaJdbc
