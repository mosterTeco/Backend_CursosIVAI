package mx.ivai;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import mx.ivai.POJO.Registro;

public class Excel {

    public static byte[] excelRegistros(ArrayList<Registro> registros, String nombreCurso) {
        try {
            String[] encabezados = {
                    "ID", "Nombre", "Apellidos", "SO", "Teléfono", "Correo", "ID Curso",
                    "Lugar de Procedencia", "Grado de Estudios", "Orden", "Área de Adquisición",
                    "Cargo Público", "Género", "Estado", "Fecha", "Recibir Información", "Intérprete"
            };
    
            Workbook libro = new XSSFWorkbook(); 
            Sheet hoja = libro.createSheet(nombreCurso);
    
            Row headerRow = hoja.createRow(0);
            for (int i = 0; i < encabezados.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(encabezados[i]);
            }
    
            for (int i = 0; i < registros.size(); i++) {
                Registro registro = registros.get(i);
                Row row = hoja.createRow(i + 1);
    
                row.createCell(0).setCellValue(registro.getIdRegistro());
                row.createCell(1).setCellValue(registro.getNombre());
                row.createCell(2).setCellValue(registro.getApellidos());
                row.createCell(3).setCellValue(registro.getSo());
                row.createCell(4).setCellValue(registro.getTelefono());
                row.createCell(5).setCellValue(registro.getCorreo());
                row.createCell(6).setCellValue(registro.getIdCurso());
                row.createCell(7).setCellValue(registro.getLugarDeProcedencia());
                row.createCell(8).setCellValue(registro.getGradoDeEstudios());
                row.createCell(9).setCellValue(registro.getOrden());
                row.createCell(10).setCellValue(registro.getAreaAdquisicion());
                row.createCell(11).setCellValue(registro.getCargoPublico());
                row.createCell(12).setCellValue(registro.getGenero());
                row.createCell(13).setCellValue(registro.getEstado());
                row.createCell(14).setCellValue(registro.getFecha());
                row.createCell(15).setCellValue(registro.getRecibirInformacion());
                row.createCell(16).setCellValue(registro.getInterprete());
            }
    
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            libro.write(outputStream);
            libro.close(); 
            return outputStream.toByteArray();
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    

        return null;

    }

}