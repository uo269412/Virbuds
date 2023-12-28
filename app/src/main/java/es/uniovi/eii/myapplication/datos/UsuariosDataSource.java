package es.uniovi.eii.myapplication.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.myapplication.modelo.Usuario;

public class UsuariosDataSource {
    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, update and delete)
     */
    private SQLiteDatabase database;

    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private MyDBHelper dbHelper;

    /**
     * Columnas de la tabla
     */
    private final String[] allColumns = { MyDBHelper.COLUMNA_USERNAME_USUARIOS, MyDBHelper.COLUMNA_NAME_USUARIOS,
            MyDBHelper.COLUMNA_BIOGRAFIA_USUARIOS,
            MyDBHelper.COLUMNA_UNIVERSIDAD_USUARIOS, MyDBHelper.COLUMNA_CARRERA_USUARIOS,
            MyDBHelper.COLUMNA_CURSO_USUARIOS, MyDBHelper.COLUMNA_FOTO_USUARIOS
    };

    /**
     * Constructor.
     *
     * @param context
     */
    public UsuariosDataSource(Context context) {
        // el último parámetro es la versión
        dbHelper = new MyDBHelper(context, null, null, 1);
    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }


    public long createUsuario(Usuario usuarioToInsert) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COLUMNA_USERNAME_USUARIOS, usuarioToInsert.getUsuario());
        values.put(MyDBHelper.COLUMNA_NAME_USUARIOS, usuarioToInsert.getNombre());
        values.put(MyDBHelper.COLUMNA_CONTRASENA_USUARIOS, usuarioToInsert.getContraseña());
        values.put(MyDBHelper.COLUMNA_BIOGRAFIA_USUARIOS, usuarioToInsert.getBiografia());
        values.put(MyDBHelper.COLUMNA_UNIVERSIDAD_USUARIOS, usuarioToInsert.getUniversidad());
        values.put(MyDBHelper.COLUMNA_CARRERA_USUARIOS, usuarioToInsert.getCarrera());
        values.put(MyDBHelper.COLUMNA_CURSO_USUARIOS, usuarioToInsert.getCurso());
        values.put(MyDBHelper.COLUMNA_FOTO_USUARIOS, usuarioToInsert.getFotoperfil());

        // Insertamos la valoracion
        long insertId = database.insert(MyDBHelper.TABLA_USUARIOS, null, values);

        return insertId;
    }

    public long createRelacionSeguido(String id_usuario, String id_seguido) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(MyDBHelper.COLUMNA_SEGUIDOR, id_usuario);
        values.put(MyDBHelper.COLUMNA_SEGUIDO, id_seguido);

        // Insertamos la valoracion
        long insertId = database.insert(MyDBHelper.TABLA_SEGUIDOS, null, values);

        return insertId;
    }

    /**
     * Obtiene todas las valoraciones andadidas por los usuarios. Sin ninguna restricción SQL
     *
     * @return Lista de objetos de tipo Pelicula
     *
     */
    public List<Usuario> getFilteredSeguidores(String username) {
        // Lista que almacenara el resultado
        List<Usuario> usersList = new ArrayList<Usuario>();
        //hacemos una query porque queremos devolver un cursor
        Cursor cursor = database.rawQuery("SELECT "+ MyDBHelper.COLUMNA_USERNAME_USUARIOS + ", "
                + MyDBHelper.COLUMNA_NAME_USUARIOS + ", " + MyDBHelper.COLUMNA_BIOGRAFIA_USUARIOS + ", "
                + MyDBHelper.COLUMNA_UNIVERSIDAD_USUARIOS + ", " + MyDBHelper.COLUMNA_CARRERA_USUARIOS + ", "
                + MyDBHelper.COLUMNA_CURSO_USUARIOS + ", " + MyDBHelper.COLUMNA_FOTO_USUARIOS
                + " FROM " + MyDBHelper.TABLA_USUARIOS + ", "+ MyDBHelper.TABLA_SEGUIDOS + " WHERE "
                + MyDBHelper.COLUMNA_SEGUIDOR + " = " + MyDBHelper.COLUMNA_USERNAME_USUARIOS + " AND "+ MyDBHelper.COLUMNA_SEGUIDO + " = ?", new String[] {username });
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Usuario usuario = new Usuario();
            usuario.setUsuario(cursor.getString(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setBiografia(cursor.getString(2));
            usuario.setUniversidad(cursor.getString(3));
            usuario.setCarrera(cursor.getString(4));
            usuario.setCurso(Integer.parseInt(cursor.getString(5)));
            usuario.setFotoperfil(cursor.getString(6));
            usersList.add(usuario);
            cursor.moveToNext();
        }
        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        Usuario cargado = getUsuario(username);
        cargado.setSeguidores(usersList);
        return cargado.getSeguidores();
    }


    public List<Usuario> getUsuarios() {
        // Lista que almacenara el resultado
        List<Usuario> usersList = new ArrayList<Usuario>();
        //hacemos una query porque queremos devolver un cursor
        Cursor cursor = database.query(MyDBHelper.TABLA_USUARIOS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Usuario usuario = new Usuario();
            usuario.setUsuario(cursor.getString(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setBiografia(cursor.getString(2));
            usuario.setUniversidad(cursor.getString(3));
            usuario.setCarrera(cursor.getString(4));
            usuario.setCurso(Integer.parseInt(cursor.getString(5)));
            usuario.setFotoperfil(cursor.getString(6));
            usersList.add(usuario);
            cursor.moveToNext();
        }
        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return usersList;
    }

    public List<Usuario> getFilteredSeguidos(String username) {
        // Lista que almacenara el resultado
        List<Usuario> usersList = new ArrayList<Usuario>();
        //hacemos una query porque queremos devolver un cursor
        Cursor cursor = database.rawQuery("SELECT "+ MyDBHelper.COLUMNA_USERNAME_USUARIOS + ", "
                + MyDBHelper.COLUMNA_NAME_USUARIOS + ", " + MyDBHelper.COLUMNA_BIOGRAFIA_USUARIOS + ", "
                + MyDBHelper.COLUMNA_UNIVERSIDAD_USUARIOS + ", " + MyDBHelper.COLUMNA_CARRERA_USUARIOS + ", "
                + MyDBHelper.COLUMNA_CURSO_USUARIOS + ", " + MyDBHelper.COLUMNA_FOTO_USUARIOS + " FROM "
                + MyDBHelper.TABLA_USUARIOS + " JOIN "+ MyDBHelper.TABLA_SEGUIDOS + " ON " +
                MyDBHelper.COLUMNA_SEGUIDO + " = " + MyDBHelper.COLUMNA_USERNAME_USUARIOS  + " WHERE "
                + MyDBHelper.COLUMNA_SEGUIDOR + " = ?", new String[] {username });
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Usuario usuario = new Usuario();
            usuario.setUsuario(cursor.getString(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setBiografia(cursor.getString(2));
            usuario.setUniversidad(cursor.getString(3));
            usuario.setCarrera(cursor.getString(4));
            usuario.setCurso(Integer.parseInt(cursor.getString(5)));
            usuario.setFotoperfil(cursor.getString(6));
            usersList.add(usuario);
            cursor.moveToNext();
        }
        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        Usuario cargado = getUsuario(username);
        cargado.setSeguidos(usersList);
        return cargado.getSeguidos();
    }

    public boolean checkCredentials(String username, String password) {
        List<Usuario> usersList = new ArrayList<Usuario>();
        //hacemos una query porque queremos devolver un cursor
        open();
        Cursor cursor = database.rawQuery("SELECT * FROM "
                + MyDBHelper.TABLA_USUARIOS + " WHERE "
                + MyDBHelper.COLUMNA_USERNAME_USUARIOS + "= ? AND "
                + MyDBHelper.COLUMNA_CONTRASENA_USUARIOS + " = ?", new String[] {username, password});
        boolean res = cursor.moveToFirst();
        cursor.close();
        return res;
    }

    public Usuario getUsuario(String username) {
        open();
        Cursor cursor = database.rawQuery("SELECT "+ MyDBHelper.COLUMNA_USERNAME_USUARIOS + ", "
                + MyDBHelper.COLUMNA_NAME_USUARIOS + ", " + MyDBHelper.COLUMNA_BIOGRAFIA_USUARIOS + ", "
                + MyDBHelper.COLUMNA_UNIVERSIDAD_USUARIOS + ", " + MyDBHelper.COLUMNA_CARRERA_USUARIOS + ", "
                + MyDBHelper.COLUMNA_CURSO_USUARIOS + ", " + MyDBHelper.COLUMNA_FOTO_USUARIOS
                + " FROM " + MyDBHelper.TABLA_USUARIOS  + " WHERE " + MyDBHelper.COLUMNA_USERNAME_USUARIOS + " = ?", new String[] {username });
        Usuario usuario = new Usuario();
        if (cursor.moveToFirst()) {
            usuario.setUsuario(cursor.getString(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setBiografia(cursor.getString(2));
            usuario.setUniversidad(cursor.getString(3));
            usuario.setCarrera(cursor.getString(4));
            usuario.setCurso(Integer.parseInt(cursor.getString(5)));
            usuario.setFotoperfil(cursor.getString(6));
            cursor.moveToNext();
        }
        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.
        return usuario;
    }
}