package com.thangpt.researching.common;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import com.thangpt.researching.exceptions.CustomizeException;
import com.thangpt.researching.model.ExcelModel;

@Component
public class ExcelConverter {

    private ExcelModel convertExcelRow2Object(Row firstRow,List<Cell> cells){
        ExcelModel excelModel = new ExcelModel();
        Field [] fields = excelModel.getClass().getDeclaredFields();
        try {
            for(Cell cell : firstRow){
                Optional<Field> field = Arrays.asList(fields).stream()
                                        .filter(x->x.getName().equalsIgnoreCase(cell.toString())).findFirst();
                if(field.isPresent()){
                    field.get().setAccessible(true);
                    if(field.get().getType() == String.class){
                        if(cells.get(cell.getColumnIndex()) == null){
                            continue;
                        } else {
                            field.get().set(excelModel, cells.get(cell.getColumnIndex()).toString());
                        }
                    } else if (field.get().getType() == Double.class){
                        if (cells.get(cell.getColumnIndex()) == null){
                            throw new CustomizeException("File format invalid!");
                        } else {
                            field.get().set(excelModel, cells.get(cell.getColumnIndex()).getNumericCellValue());
                        }
                    }
                }
            }
        } catch (Exception e){
            throw new CustomizeException("File format invalid!");
        }
        return excelModel;
    }

    private ExcelModel convertExcelRow2Object(Row firstRow,Row currentRow){
        ExcelModel excelModel = new ExcelModel();
        Field [] fields = excelModel.getClass().getDeclaredFields();
        try {
            for(Cell cell : firstRow){
                Optional<Field> field = Arrays.asList(fields).stream()
                                        .filter(x->x.getName().equalsIgnoreCase(cell.toString())).findFirst();
                if(field.isPresent()){
                    field.get().setAccessible(true);
                    if(field.get().getType() == String.class){
                        field.get().set(excelModel, currentRow.getCell(cell.getColumnIndex()).toString());
                    } else if (field.get().getType() == Double.class){
                        field.get().set(excelModel, currentRow.getCell(cell.getColumnIndex()).getNumericCellValue());
                    }
                }
            }
        } catch (Exception e){
            throw new CustomizeException("File format invalid!");
        }
        return excelModel;
    }

    public Object convertExcelToObject(Row firstRow,Row currentRow, Class<?> responseType) {
        try {
            Field [] fields = responseType.getDeclaredFields();
            Object result = responseType.getDeclaredConstructor().newInstance();
            for(Cell cell : firstRow){
                Optional<Field> field = Arrays.asList(fields).stream()
                                        .filter(x->x.getName().equalsIgnoreCase(cell.toString())).findFirst();
                if(field.isPresent()){
                    field.get().setAccessible(true);
                    if(field.get().getType() == String.class){
                        field.get().set(result, currentRow.getCell(cell.getColumnIndex()).toString());
                    } else if (field.get().getType() == Double.class){
                        field.get().set(result, currentRow.getCell(cell.getColumnIndex()).getNumericCellValue());
                    }
                }
            }
            return result;
        } catch (Exception e){
            throw new CustomizeException("File format invalid! Error: "+ e.getStackTrace());
        }
    }
    
}
