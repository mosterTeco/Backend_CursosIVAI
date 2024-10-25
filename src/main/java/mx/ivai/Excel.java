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
                    "Id del Registro", "Nombre", "Apellidos", "Sujeto Obligado", "Teléfono", "Correo", "Id Curso", "Nombre del Curso",
                    "Lugar de Procedencia", "Grado de Estudios", "Orden de Gobierno", "Área de Adquisición",
                    "Cargo Público", "Género", "Estado", "Fecha de Registro", "Desea Recibir Información", "Necesita un Intérprete", "Asistencia"
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
                row.createCell(7).setCellValue(registro.getNombreCurso());
                row.createCell(8).setCellValue(registro.getLugarDeProcedencia());
                row.createCell(9).setCellValue(registro.getGradoDeEstudios());
                row.createCell(10).setCellValue(registro.getOrden());
                row.createCell(11).setCellValue(registro.getAreaAdquisicion());
                row.createCell(12).setCellValue(registro.getCargoPublico());
                row.createCell(13).setCellValue(registro.getGenero());
                row.createCell(14).setCellValue(registro.getEstado());
                row.createCell(15).setCellValue(registro.getFecha());
                if(registro.getRecibirInformacion().equals("true"))
                    row.createCell(16).setCellValue("Sí");
                else   
                    row.createCell(16).setCellValue("No");
                if(registro.getInterprete().equals("true"))
                    row.createCell(17).setCellValue("Sí");
                else   
                    row.createCell(17).setCellValue("No");
                if(registro.getAsistencia().equals("true"))
                    row.createCell(18).setCellValue("Sí");
                else   
                    row.createCell(18).setCellValue("No");
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