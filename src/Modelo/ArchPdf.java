

package Modelo;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import static java.awt.Frame.NORMAL;
import java.awt.Image;
//archivos
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
//librerias ajenas a itext
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.tools.jar.Main;
/**
 * Esta clase es la encargada de generar los pdfs de cada reporte 
 * @web http://jc-mouse.blogspot.com/
 * @author Mouse
 */
public class ArchPdf {
    //objeto privado que contiene un file para la ruta de destino de los pdfs
    private File ruta_destino;
    /**
     * El constructor basico permitira crear una instancia 
     * de tipo ArchPdf
     */
    public ArchPdf(){
        ruta_destino=null;
    }

    /**
     * Se encargar de crear un pdf con la factura de un envio, que contiene codigo de barras,
     * codigo QR y toda la informacion respectiva en celdas de tablas diferentes
     * return void
     */
     public void PDFfactura(String[] tabla1,String[] tabla2,String[] tabla3,String NroEnvio,String Info){
        String f=fecha();
        this.ruta_destino =new  File("4. Reportes Facturas/Factura "+NroEnvio+".pdf");
        if(this.ruta_destino!=null){
            Document mipdf=new Document();
            try {
                PdfWriter pf=PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino));
                mipdf.open();
                mipdf.addTitle("Factura");     
                
                String codigo=codigoRandom();
                com.itextpdf.text.Image nuevo=codigoQR(codigo); 
                com.itextpdf.text.Image nuevo2=getBarraCodigo(mipdf,pf,codigo);
                nuevo2.setAlignment(com.itextpdf.text.Image.ALIGN_RIGHT);   
                
                PdfPTable tblImages=new PdfPTable(2);
                PdfPCell cellOne = new PdfPCell(nuevo);
                PdfPCell cellTwo = new PdfPCell(nuevo2);             
                cellOne.setBorder(Rectangle.NO_BORDER);
                cellTwo.setBorder(Rectangle.NO_BORDER);               
                tblImages.addCell(cellOne);
                tblImages.addCell(cellTwo);
                mipdf.add(tblImages);             
                
                Font ti = new Font(Font.FontFamily.TIMES_ROMAN,26,Font.BOLD);                              
                Paragraph p = new Paragraph("FACTURA",ti);
                p.setAlignment(Paragraph.ALIGN_CENTER);
                mipdf.add(p);
                mipdf.add(Chunk.NEWLINE);                
                
                PdfPTable tbl=new PdfPTable(1);
                tbl.addCell(tabla1[0]);
                mipdf.add(tbl);               
                
                PdfPTable tbl1=new PdfPTable(1);
                tbl1.addCell(tabla2[0]);               
                mipdf.add(tbl1);                
                
                PdfPTable tbl2=new PdfPTable(5);
                tbl2.addCell("Codigo");
                tbl2.addCell("Descripcion");
                tbl2.addCell("Kilos");
                tbl2.addCell("Origen");
                tbl2.addCell("Destino");
                tbl2.addCell(tabla3[0]);
                tbl2.addCell(tabla3[1]);
                tbl2.addCell(tabla3[2]);
                tbl2.addCell(tabla3[3]);
                tbl2.addCell(tabla3[4]);         
                mipdf.add(tbl2);               
                mipdf.add(Chunk.NEWLINE);
                
                PdfPTable tbl3=new PdfPTable(2);
                tbl3.addCell("Valor Envio: ");                
                tbl3.addCell(Info);               
                mipdf.add(tbl3);                  
                           
                mipdf.close();
                abrirPDF("listaClientes "+f+".pdf");
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ArchPdf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Se encargar de crear un pdf con una tabla donde estan registrados todos los clientes
     * se crea un objeto de tipoPdfPTable y agregar toda la informacion que recibe en el objeto JTable tabla
     * return void
     */
     public void JtablePDFCliente(JTable tabla){
        String f=fecha();
        this.ruta_destino =new  File("1. Reportes Clientes/listaClientes "+f+".pdf");
        if(this.ruta_destino!=null){
            Document mipdf=new Document();
            try {
                PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino));
                mipdf.open();

                mipdf.addTitle("PDF Clientes");
                Font ti = new Font(Font.FontFamily.TIMES_ROMAN,26,Font.BOLD);                 
                Paragraph p = new Paragraph("LISTADO CLIENTES",ti);
                p.setAlignment(Paragraph.ALIGN_CENTER);
                mipdf.add(p);
                com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("Imagenes/cliente.jpg");
                img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
                img.scalePercent(60);                
                mipdf.add(img);
                
                mipdf.add(Chunk.NEWLINE); //genera una linea en blanco en el pdf
                PdfPTable tbl=new PdfPTable(3);
                
                tbl.addCell("Identificacion");
                tbl.addCell("Nombre");
                tbl.addCell("Telefono");
                
                for(int x=0;x<tabla.getRowCount();x++){
                    String Id=tabla.getValueAt(x,0).toString();
                    String Nombre=tabla.getValueAt(x,1).toString();
                    String Tel=tabla.getValueAt(x,2).toString();
                    
                    tbl.addCell(Id);
                    tbl.addCell(Nombre);
                    tbl.addCell(Tel);  
                }
                mipdf.add(tbl);
                mipdf.add(Chunk.NEWLINE);
                mipdf.add(Chunk.NEWLINE);
                mipdf.close();
                abrirPDF("listaClientes "+f+".pdf");
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ArchPdf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     /**
     * Se encargar de crear un pdf con una tabla donde estan registrados toda la mercancia
     * se crea un objeto de tipoPdfPTable y agregar toda la informacion que recibe en el objeto JTable tabla
     * return void
     */
     public void JtablePDFMercancia(JTable tabla){
        String f=fecha();
        this.ruta_destino =new  File("2. Reportes Mercancia/listaMercancia "+f+".pdf");
        if(this.ruta_destino!=null){
            Document mipdf=new Document();
            try {
                PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino));
                mipdf.open();
              
                mipdf.addTitle("Lista Mercancia");                  
                Font ti = new Font(Font.FontFamily.TIMES_ROMAN,26,Font.BOLD);                              
                Paragraph p = new Paragraph("LISTADO MERCANCIA",ti);
                p.setAlignment(Paragraph.ALIGN_CENTER);
                mipdf.add(p);
                com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("Imagenes/mercancia.jpg");
                img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
                img.scalePercent(80);                
                mipdf.add(img);
               
                mipdf.add(Chunk.NEWLINE); //genera una linea en blanco en el pdf
                PdfPTable tbl=new PdfPTable(3);
                
                tbl.addCell("Codigo");
                tbl.addCell("Descripcion");
                tbl.addCell("Kilos");
                
                for(int x=0;x<tabla.getRowCount();x++){
                    String Cod=tabla.getValueAt(x,0).toString();
                    String Desc=tabla.getValueAt(x,1).toString();
                    String Kilos=tabla.getValueAt(x,2).toString();
                    
                    tbl.addCell(Cod);
                    tbl.addCell(Desc);
                    tbl.addCell(Kilos);  
                }
                mipdf.add(tbl);
                mipdf.add(Chunk.NEWLINE);
                mipdf.add(Chunk.NEWLINE);
                mipdf.close();
                abrirPDF("listaMercancia "+f+".pdf");
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ArchPdf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     /**
     * Se encargar de crear un pdf con una tabla donde estan registrados tods los envios
     * se crea un objeto de tipoPdfPTable y agregar toda la informacion que recibe en el objeto JTable tabla
     * return void
     */     
    public void JtablePDFEnvio(JTable tabla,String info){
        String f=fecha();
        this.ruta_destino =new  File("3. Reportes Envios/listaEnvios "+f+".pdf");
        if(this.ruta_destino!=null){
            Document mipdf=new Document(PageSize.A4.rotate());
            try {
                PdfWriter.getInstance(mipdf, new FileOutputStream(this.ruta_destino));
                mipdf.open();
                
                mipdf.addTitle("Lista Envios");                  
                Font ti = new Font(Font.FontFamily.TIMES_ROMAN,26,Font.BOLD);                              
                Paragraph p = new Paragraph("LISTADO ENVIOS",ti);
                p.setAlignment(Paragraph.ALIGN_CENTER);
                mipdf.add(p);
                com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance("Imagenes/envios.jpg");
                img.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
                img.scalePercent(80);                
                mipdf.add(img);                
                
                mipdf.add(Chunk.NEWLINE); //genera una linea en blanco en el pdf
                PdfPTable tbl=new PdfPTable(7);
                
                tbl.addCell("Nro Envio");
                tbl.addCell("Id Cliente");
                tbl.addCell("Nombre Cliente");
                tbl.addCell("Cod Mercancia");
                tbl.addCell("Desc Merc.");
                tbl.addCell("Kilos");
                tbl.addCell("Origen");
                tbl.addCell("Destino");
                tbl.addCell("Valor Envio");
                
                for(int x=0;x<tabla.getRowCount();x++){
                    String NroEnvio=tabla.getValueAt(x,0).toString();
                    String IdCli=tabla.getValueAt(x,1).toString();
                    String NombreCli=tabla.getValueAt(x,2).toString();
                    String CodMerc=tabla.getValueAt(x,3).toString();
                    String DesMec=tabla.getValueAt(x,4).toString();
                    String Kilos=tabla.getValueAt(x,5).toString();
                    String Origen=tabla.getValueAt(x,6).toString();
                    String Destino=tabla.getValueAt(x,7).toString();
                    String VE=tabla.getValueAt(x,8).toString();
                    
                    tbl.addCell(NroEnvio);
                    tbl.addCell(IdCli);
                    tbl.addCell(NombreCli);
                    tbl.addCell(CodMerc);
                    tbl.addCell(DesMec);
                    tbl.addCell(Kilos);
                    tbl.addCell(Origen); 
                    tbl.addCell(Destino); 
                    tbl.addCell(VE);  
                }
                mipdf.add(tbl);
                mipdf.add(Chunk.NEWLINE);
                mipdf.add(Chunk.NEWLINE);
                mipdf.add(new Paragraph(info));
                mipdf.close();
                abrirPDF("listaEnvios "+f+".pdf");
            } catch (DocumentException | FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,"Error "+ex.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(ArchPdf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Se encargar de generar el codigo de barras y devolverlo en tipo imagen
     * @param doc de tipo Document
     * @param pw de tipo PdfWriter
     * @param Codigo de tipo String
     * @return com.itextpdf.text.Image
     */
             
    private com.itextpdf.text.Image getBarraCodigo(Document doc,PdfWriter pw,String Codigo){
        PdfContentByte cimg=pw.getDirectContent();
        Barcode128 code128=new Barcode128();
        code128.setCode(formatearCodigo(Codigo));
        code128.setCodeType(Barcode128.CODE128);
        code128.setTextAlignment(Element.ALIGN_RIGHT);
        
        com.itextpdf.text.Image image=code128.createImageWithBarcode(cimg, BaseColor.BLACK, BaseColor.BLACK);
        image.scalePercent(160);
        image.setAlignment(Element.ALIGN_RIGHT);
        return image;
    }
    /**
     * Se encargar de formatear el codigo que va en codigode barras
     * @param num de tipo String
     */
    private String formatearCodigo(String num){
        NumberFormat form= new DecimalFormat("0000000");
        return form.format((num!=null) ? Integer.parseInt(num) : 0000000);
    } 
    /**
     * Se encargar de generar un codigo para el convertirlo en codigo de barras o codigo QR
     * @return String
     */
    public String codigoRandom(){
        String v="";
        for(int x=0;x<6;x++){
            v+= (int) (10 * Math.random());
        }     
        return v;
    }
    /**
     * Se encargar de generar el codigo QR en formato de imagen
     * @param codigo String
     * @return com.itextpdf.text.Image
     * @throws com.itextpdf.text.BadElementException
     */
    public com.itextpdf.text.Image codigoQR(String codigo) throws BadElementException{
        BarcodeQRCode qr=new BarcodeQRCode(codigo,80,80,null);
        return qr.getImage();
    }
    /**
     * Se encargar de devolver un string con la fecha actual del sistema 
     * @return String
     */
    public String fecha(){
        Calendar fecha = Calendar.getInstance();
        String f=fecha.get(Calendar.DAY_OF_MONTH)
                +"-"+fecha.get((Calendar.MONTH)+1)
                +"-"+fecha.get(Calendar.YEAR)
                +"("+fecha.get(Calendar.HOUR_OF_DAY)
                +"."+fecha.get(Calendar.MINUTE)
                +"."+fecha.get(Calendar.SECOND)+")";
        return f;
    }
    /**
     * Se encargar de abrir un pdf en la direccion que le envian en el parametro info
     * @param info String
     */
    public void abrirPDF(String info){
        try {
            Desktop.getDesktop().open(this.ruta_destino);
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
    /* abre la ventana de dialogo GUARDAR*/
    public void Colocar_Destino(){
       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo PDF","pdf","PDF");//tipos de archivo
       JFileChooser fileChooser = new JFileChooser();//ventana de busqueda de archivos
       fileChooser.setFileFilter(filter);
       int result = fileChooser.showSaveDialog(null);
       if ( result == JFileChooser.APPROVE_OPTION ){//si damos en guardar, se aprueba la ruta de almacenamiento
           this.ruta_destino = fileChooser.getSelectedFile().getAbsoluteFile();//ruta destino
        }
    }
    /**
     *Retorna el valor de tipo File del Ruta_destino
     * @return String
     */
    public File getRuta_destino() {
        return ruta_destino;
    }
    /**
     * Establece o mofidica el contenido del atributo 
     * Ruta_destino con el valor que recibe en formato de
     * File
     * @param ruta_destino de tipo File
     */
    public void setRuta_destino(File ruta_destino) {
        this.ruta_destino = ruta_destino;
    }
    
}