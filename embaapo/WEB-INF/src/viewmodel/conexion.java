package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class conexion {

    private String url;
    private String usuario;
    private String contrasenia;
    private static Connection conexion;

    public conexion() {
        this.url = "jdbc:postgresql://localhost:5432/tp";
        this.usuario = "postgres";
        this.contrasenia = "0077";
        this.conexion = null;
    }

    public conexion(String url, String usuario, String contrasenia) {
        this.url = "jdbc:postgresql://localhost:5432/tp";
        this.usuario = "postgres";
        this.contrasenia = "0077";
        this.conexion = null;
    }

    public void crearConexion() {
        try {
            // Se define el driver a utilizar (postgresql)
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Se crea la conexión
        try {
            // Conexión con la base de datos
            conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasenia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public boolean validarCredenciales(String email, String password) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                    "0077");
            System.out.println("Conecto");
            // Consulta para verificar las credenciales
            String consulta = "SELECT * FROM usuario WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();

                }

            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }

    }

    // Método para cargar los sellers desde la base de datos
    public List<Map<String, Object>> obtenerSellers() {
        List<Map<String, Object>> listaSellers = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT id_seller, nombre, oficio FROM seller";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> seller = new HashMap<>();
                        seller.put("id_seller", resultSet.getInt("id_seller"));
                        seller.put("nombre", resultSet.getString("nombre"));
                        seller.put("oficio", resultSet.getString("oficio"));
                        listaSellers.add(seller);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaSellers;
    }

    // Método para cargar los sellers desde la base de datos
    public List<Map<String, Object>> obtenerCalificaciones() {
        List<Map<String, Object>> listaCalificaciones = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT id_calificacion, id_seller, descripcion,puntuacion  FROM calificacion";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> calificacion = new HashMap<>();
                        calificacion.put("id_calificacion", resultSet.getInt("id_calificacion"));
                        calificacion.put("id_seller", resultSet.getInt("id_seller"));
                        calificacion.put("descripcion", resultSet.getString("descripcion"));
                        calificacion.put("puntuacion", resultSet.getInt("puntuacion"));
                        listaCalificaciones.add(calificacion);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaCalificaciones;
    }
/* 
    public static void main(String[] args) {
        conexion viewModel = new conexion();

        List<Map<String, Object>> calificaciones = viewModel.obtenerCalificaciones();

        // Imprimir los resultados
        System.out.println("Lista de Calificaciones:");
        for (Map<String, Object> calificacion : calificaciones) {
            System.out.println("ID: " + calificacion.get("id_calificacion"));
            System.out.println("ID Seller: " + calificacion.get("id_seller"));
            System.out.println("Descripción: " + calificacion.get("descripcion"));
            System.out.println("Puntuación: " + calificacion.get("puntuacion"));
            System.out.println("-------------");
        }
    }
/* */
    // Método para cargar los sellers desde la base de datos
    public List<Map<String, Object>> obtenerMonedas() {
        List<Map<String, Object>> listaMonedas = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT id_diviza, nombre, simbolo FROM diviza";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> moneda = new HashMap<>();
                        moneda.put("id_diviza", resultSet.getInt("id_diviza"));
                        moneda.put("nombre", resultSet.getString("nombre"));
                        moneda.put("simbolo", resultSet.getString("simbolo"));
                        listaMonedas.add(moneda);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaMonedas;
    }

    private String titulo;
    private String descripcion;
    private int tarifa;
    private Instant fecha_insersion = Instant.now();
    private Instant fecha_mod = Instant.now();
    private conexion connect;

    public List<Map<String, Object>> obtenerServicios() {
        List<Map<String, Object>> listaServicios = new ArrayList<>();
        //System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = " SELECT sa.id_servicio, sa.titulo, sa.tarifa," +
                    "       s.id_seller, s.nombre, s.direccion, s.oficio" +
                    " FROM seller_anuncio sa" +
                    " JOIN seller s ON sa.id_seller = s.id_seller;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> servicio = new HashMap<>();
                        servicio.put("id_servicio", resultSet.getInt("id_servicio"));
                        servicio.put("id_seller", resultSet.getInt("id_seller"));
                        servicio.put("titulo", resultSet.getString("titulo"));
                        servicio.put("tarifa", resultSet.getInt("tarifa"));
                        servicio.put("nombre", resultSet.getString("nombre"));
                        servicio.put("direccion", resultSet.getString("direccion"));
                        servicio.put("oficio", resultSet.getString("oficio"));
                        listaServicios.add(servicio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
       // System.out.println("Salio en la carga");

        return listaServicios;
    }

    public List<Map<String, Object>> buscarServicios(String terminoBusqueda) {
        List<Map<String, Object>> listaServicios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077")) {
            String sql = "SELECT sa.id_servicio, sa.titulo, sa.tarifa, s.id_seller, s.nombre, s.direccion, s.oficio " +
                    "FROM seller_anuncio sa " +
                    "JOIN seller s ON sa.id_seller = s.id_seller " +
                    "WHERE lower(sa.titulo) LIKE ?"; // Utilizamos LOWER para hacer la búsqueda insensible a mayúsculas
                                                     // y minúsculas

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                System.out.println(" Termino Busqueda :" + terminoBusqueda);
                System.out.println(" preparedStatement  :" + preparedStatement);

                preparedStatement.setString(1, "%" + terminoBusqueda.toLowerCase() + "%"); // Agregamos % para la
                                                                                           // búsqueda parcial
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> servicio = new HashMap<>();
                        servicio.put("id_servicio", resultSet.getInt("id_servicio"));
                        servicio.put("id_seller", resultSet.getInt("id_seller"));
                        servicio.put("titulo", resultSet.getString("titulo"));
                        servicio.put("tarifa", resultSet.getInt("tarifa"));
                        servicio.put("nombre", resultSet.getString("nombre"));
                        servicio.put("direccion", resultSet.getString("direccion"));
                        servicio.put("oficio", resultSet.getString("oficio"));
                        listaServicios.add(servicio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }

        return listaServicios;
    }
    public List<Map<String, Object>> obtenerAnunciosUsuarios() {
        List<Map<String, Object>> listaAnunciosUsuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077")) {
            
            String sql = "SELECT ac.titulo AS titulo, ac.descripcion AS descripcion, ac.direccion AS direccion , ac.id_usuario AS id_usuario, u.nombre AS nombre " +
             "FROM anuncio_cliente ac " +
             "JOIN usuario u ON ac.id_usuario = u.id_usuario";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> anuncio = new HashMap<>();
                        anuncio.put("titulo", resultSet.getString("titulo"));
                        anuncio.put("descripcion", resultSet.getString("descripcion"));
                        anuncio.put("direccion", resultSet.getString("direccion"));
                        anuncio.put("id_usuario", resultSet.getInt("id_usuario"));
                        anuncio.put("nombre", resultSet.getString("nombre"));
                        listaAnunciosUsuarios.add(anuncio);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAnunciosUsuarios;
    }
    public List<Map<String, Object>> buscarAnuncios(String terminoBusqueda) {
        List<Map<String, Object>> listaAnunciosUsuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077")) {
            String sql = " SELECT ac.titulo, ac.descripcion, ac.direccion, ac.id_anuncio_cliente, ac.id_usuario, u.nombre" +
                    " FROM anuncio_cliente ac " +
                    " JOIN usuario u ON ac.id_usuario = u.id_usuario " +
                    " WHERE lower(ac.titulo) LIKE ?"; // Utilizamos LOWER para hacer la búsqueda insensible a mayúsculas
                                                    //  y minúsculas

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                System.out.println(" Termino Busqueda :" + terminoBusqueda);
                System.out.println(" preparedStatement  :" + preparedStatement);

                preparedStatement.setString(1, "%" + terminoBusqueda.toLowerCase() + "%"); // Agregamos % para la
                                                                                           // búsqueda parcial
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> anuncio = new HashMap<>();
                        anuncio.put("titulo", resultSet.getString("titulo"));
                        anuncio.put("descripcion", resultSet.getString("descripcion"));
                        anuncio.put("direccion", resultSet.getString("direccion"));
                        anuncio.put("id_usuario", resultSet.getInt("id_usuario"));
                        anuncio.put("nombre", resultSet.getString("nombre"));
                        listaAnunciosUsuarios.add(anuncio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }

        return listaAnunciosUsuarios;
    }

    boolean registrarEnBaseDeDatos(String titulo, String descripcion, int tarifa) {
        System.out.println("Datos ingresados:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                    "0077");
            Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO seller_anuncio (titulo ,descripcion, tarifa,fecha_insersion,fecha_mod) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setInt(3, tarifa);
                preparedStatement.setObject(4, timestampInsersion);
                preparedStatement.setObject(5, timestampMod);
                // Ejecutar la inserción
                int filasAfectadas = preparedStatement.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }
    }

    private int id_servicio;

    boolean actualizarAnuncioSeller(int id_servicio, String titulo, String descripcion, int tarifa, Instant fecha_mod) {
        System.out.println("Datos ingresados:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);
        System.out.println("Fecha de Insersión: " + fecha_mod);
    
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077")) {
            Timestamp timestampMod = Timestamp.from(fecha_mod);
    
            // Consulta para actualizar el anuncio del seller
            String consulta = "UPDATE seller_anuncio SET titulo = ?, descripcion = ?, tarifa = ?, fecha_mod = ? WHERE id_servicio = ?";
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setInt(3, tarifa);
                preparedStatement.setObject(4, timestampMod);
                preparedStatement.setInt(5, id_servicio);
    
                int filasAfectadas = preparedStatement.executeUpdate();
                return filasAfectadas > 0; // Devuelve true si se actualizó al menos una fila
            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        // Datos de prueba
        int id_servicio = 1;  // Reemplaza con el id_servicio que deseas actualizar
        String nuevoTitulo = "Nuevo Titulo";
        String nuevaDescripcion = "Nueva Descripcion";
        int nuevaTarifa = 200;
        Instant nuevaFechaMod = Instant.now();  // Reemplaza con la fecha de modificación deseada
    
        // Crear una instancia de la clase para acceder al método
        conexion conexion = new conexion();  // Reemplaza con el nombre de tu clase
    
        // Llamar al método de actualización con los datos de prueba
        boolean resultado = conexion.actualizarAnuncioSeller(id_servicio, nuevoTitulo, nuevaDescripcion, nuevaTarifa, nuevaFechaMod);
    
        // Imprimir el resultado del test
        if (resultado) {
            System.out.println("El anuncio del seller se actualizó correctamente.");
        } else {
            System.out.println("Hubo un error al actualizar el anuncio del seller.");
        }
    }
    

}
