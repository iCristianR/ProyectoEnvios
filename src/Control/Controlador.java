
package Control;

import Modelo.*;
import Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener,Runnable{
    frmPrincipal frmP;
    frmCrud frmCrud;
    frmRegMercancia frmMer;
    frmRegCliente frmCli;
    frmRegEnvio frmEnv;
    frmAcerca frmAcer;
    ArchPdf pdf;
    Reporte objR;
    ClienteDAO clienteDAO;
    MercanciaDAO mercanciaDAO; 
    EnvioDAO envioDAO;
    FechayHora objH;
    Thread Hilo;    
    ArrayList<Cliente> arrayC;
    ArrayList<Mercancia> arrayM;
    
    public Controlador(){
        this.frmP = new frmPrincipal();
        this.frmCrud = new frmCrud();
        this.frmCli = new frmRegCliente();
        this.frmEnv = new frmRegEnvio();
        this.frmMer = new frmRegMercancia();
        this.frmAcer = new frmAcerca();
        this.objR = new Reporte();
        this.pdf = new ArchPdf();
        this.clienteDAO = new ClienteDAO();
        this.mercanciaDAO = new MercanciaDAO();
        this.envioDAO = new EnvioDAO();
        this.objH = new FechayHora();
        this.Hilo = new Thread(this);        
        arrayC = new ArrayList<>();
        arrayM = new ArrayList<>();
        
        this.frmCli.getBtnReg().addActionListener(this);
        this.frmEnv.getBtnReg().addActionListener(this);
        this.frmMer.getBtnReg().addActionListener(this);
        this.frmEnv.getBtnBCli().addActionListener(this);
        this.frmEnv.getBtnBMerc().addActionListener(this);
        this.frmEnv.getBtnVKilos().addActionListener(this);        
        
        this.frmCrud.getBtnActualizarCli().addActionListener(this);
        this.frmCrud.getBtnBuscarCli().addActionListener(this);
        this.frmCrud.getBtnEliminarCli().addActionListener(this);
        this.frmCrud.getBtnInsertarCli().addActionListener(this);
        this.frmCrud.getBtnPdfCli().addActionListener(this);
        this.frmCrud.getBtnActualizarMerc().addActionListener(this);
        this.frmCrud.getBtnBuscarMerc().addActionListener(this);
        this.frmCrud.getBtnEliminarMerc().addActionListener(this);
        this.frmCrud.getBtnInsertarMerc().addActionListener(this);
        this.frmCrud.getBtnPdfMerc().addActionListener(this);
        this.frmCrud.getBtnActualizarEnv().addActionListener(this);
        this.frmCrud.getBtnBuscarEnv().addActionListener(this);
        this.frmCrud.getBtnEliminarEnv().addActionListener(this);
        this.frmCrud.getBtnInsertarEnv().addActionListener(this);
        this.frmCrud.getBtnPdfEnv().addActionListener(this);
        
        this.frmP.getMnuRegCliente().addActionListener(this); 
        this.frmP.getMnuLCli().addActionListener(this);
        this.frmP.getMnuRegMercancia().addActionListener(this);
        this.frmP.getMnuRegEnvio().addActionListener(this);
        this.frmP.getMnuCRUD().addActionListener(this);
        this.frmP.getMnuSalir().addActionListener(this);
        this.frmP.getMnuAcerca().addActionListener(this);
        
        frmP.getJpnEscritorio().add(frmMer);  
        frmP.getJpnEscritorio().add(frmCli);
        frmP.getJpnEscritorio().add(frmEnv);        
        frmP.getJpnEscritorio().add(frmCrud); 
        frmP.getJpnEscritorio().add(frmAcer);
        
    } 
    
    public void iniciar(){
        frmP.setVisible(true);
        this.frmP.setTitle("Envios");
        this.frmP.setLocationRelativeTo(null);
        frmCrud.getTblCli().setModel(clienteDAO.consulta());
        frmCrud.getTblMerc().setModel(mercanciaDAO.consulta());
        frmCrud.getTblEnvios().setModel(envioDAO.consulta());
        cargarCli(); // agrega los clientes al array del jtable 
        cargarMerc();
        cargarEnv();
        run();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        /////////////////////////////////////////////////////////////////////CLIENTE
        if(e.getSource().equals(frmP.getMnuRegCliente())){     
            frmCli.setVisible(true);
            frmMer.setVisible(false);  
            frmEnv.setVisible(false);  
            frmCrud.setVisible(false);  
            frmCli.getBtnReg().setText("Registrar");
        }
        if(e.getSource().equals(frmCli.getBtnReg())){
            try {
                if(frmCli.getBtnReg().getText().equals("Registrar")){
                    Cliente objC = new Cliente();
                    objC.setId(frmCli.getTxtId().getText());
                    validarID(objC.getId());
                    objC.setNom(frmCli.getTxtNom().getText());
                    objC.setTel(frmCli.getTxtTel().getText());
                    envioTablaCli(objC, frmCrud.getTblCli());
                    arrayC.add(objC);
                    clienteDAO.insertar(objC);
                    JOptionPane.showMessageDialog(frmP,"Cliente registrado Correctamente!");
                    limpiarVentanaCli(); 
                    frmCli.setVisible(false);
                }               
            } catch (EnterFormatException ex) {
                JOptionPane.showMessageDialog(frmP,"Se ha generado un error por: "+ex.getMessage());
            }           
        }        
        //////////////////////////////////////////////////////////////////////////MERCANCIA     
        if(e.getSource().equals(frmP.getMnuRegMercancia())){
             
            frmCli.setVisible(false);
            frmMer.setVisible(true);  
            frmEnv.setVisible(false);  
            frmCrud.setVisible(false);                
        }
        if(e.getSource().equals(frmMer.getBtnReg())){
            try {
                if(frmMer.getBtnReg().getText().equals("Registrar")){
                    Mercancia objM =new Mercancia();
                    objM.setCod(frmMer.getTxtCod().getText());
                    objM.setDescr(frmMer.getTxtADes().getText());
                    objM.setKilos(Double.parseDouble(frmMer.getJspKilos().getValue().toString()));
                    envioTablaMerc(objM,frmCrud.getTblMerc());
                    arrayM.add(objM);
                    mercanciaDAO.insertar(objM);
                    JOptionPane.showMessageDialog(frmP,"Mercancia registrada Correctamente!");
                    limpiarVentanaMer();
                    frmMer.setVisible(false);
                } 
            } catch (EnterFormatException ex) {
                JOptionPane.showMessageDialog(frmEnv,"Error: "+ex.getMessage());
            }
           
        }
        //////////////////////////////////////////////////////////////////////////ENVIO
        if(e.getSource().equals(frmP.getMnuRegEnvio())){               
            frmCli.setVisible(false);
            frmMer.setVisible(false);  
            frmEnv.setVisible(true);  
            frmCrud.setVisible(false);  
            frmEnv.getTxtCodMer().setEnabled(false);
            frmEnv.getTxtDestino().setEnabled(false);
            frmEnv.getTxtOrigen().setEnabled(false);
            frmEnv.getCmbEnvio().setEnabled(false);
            frmEnv.getBtnReg().setEnabled(false);
            frmEnv.getBtnBMerc().setEnabled(false);
            frmEnv.getTxtKilos().setEnabled(false);
            frmEnv.getBtnVKilos().setEnabled(false);
         }
         if(e.getSource().equals(frmEnv.getBtnBCli())){
            if(buscarCli(frmEnv.getTxtIdCli().getText())){
                frmEnv.getTxtCodMer().setEnabled(true);
                frmEnv.getBtnBMerc().setEnabled(true);
                //JOptionPane.showMessageDialog(frmP,"Se ha encontrado el Cliente"); 
                for(int i=0;i<arrayC.size();i++){
                    if(arrayC.get(i).getId().equals(frmEnv.getTxtIdCli().getText())){
                        JOptionPane.showMessageDialog(frmP,"CLIENTE ENCONTRADO\n"+arrayC.get(i).toString()); 
                    }
                }
            }else{
                JOptionPane.showMessageDialog(frmP,"No se ha resgistrado un cliente con ese Id"); 
                frmEnv.getTxtIdCli().setText("");
            }
         }
         if(e.getSource().equals(frmEnv.getBtnBMerc())){
            if(buscarMerc(frmEnv.getTxtCodMer().getText())){
                frmEnv.getTxtKilos().setEnabled(true);
                frmEnv.getBtnVKilos().setEnabled(true);                
                //JOptionPane.showMessageDialog(frmP,"Se ha encontrado la mercancia");  
                for(int i=0;i<arrayM.size();i++){
                    if(arrayM.get(i).getCod().equals(frmEnv.getTxtCodMer().getText())){
                        JOptionPane.showMessageDialog(frmP,"MERCANCIA ENCONTRADA\n"+arrayM.get(i).toString()); 
                    }
                }
            }else{
                JOptionPane.showMessageDialog(frmP,"No se ha resgistrado mercancia asociada a ese codigo");
                frmEnv.getTxtCodMer().setText("");
            } 
         }
        if(e.getSource().equals(frmEnv.getBtnVKilos())){
            if(validarKilos(Integer.parseInt(frmEnv.getTxtKilos().getText()))){
                frmEnv.getBtnReg().setEnabled(true);
                frmEnv.getTxtDestino().setEnabled(true);
                frmEnv.getTxtOrigen().setEnabled(true);
                frmEnv.getCmbEnvio().setEnabled(true);
                JOptionPane.showMessageDialog(frmP,"Cantidad disponible de esa mercancia");                                
            }else{
                JOptionPane.showMessageDialog(frmP,"No hay la cantidad disponible de esa mercancia");  
                frmEnv.getTxtKilos().setText("");
            } 

         }
         if(e.getSource().equals(frmEnv.getBtnReg())){
            try {
                if(frmEnv.getBtnReg().getText().equals("Registrar")){
                  String Envio="";
                  Envio objE;
                  if(frmEnv.getCmbEnvio().equals("Aereo")){
                      objE = new Aereo();
                      Envio="Aereo";
                  }else{
                      objE = new Terrestre();
                      Envio="Terrestre";
                  }
                  frmEnv.getLblNro().setText("123"+(int)(Math.random()*99));
                  objE.setNroE(frmEnv.getLblNro().getText());
                  objE.setCliente(traerCliente(frmEnv.getTxtIdCli().getText()));
                  objE.setMerc(traerMercancia(frmEnv.getTxtCodMer().getText()));
                  objE.setDestino(frmEnv.getTxtDestino().getText());
                  objE.setOrigen(frmEnv.getTxtOrigen().getText());
                  envioTablaEnv(objE,frmCrud.getTblEnvios(),Envio);
                  objR.getListaE().add(objE);
                  String info1[] = {"EMPRESA: \n\nEnvios A.T\nNIT: 12346789\nTelefono: 666666\nBogota Colombia"};
                  String info2[] = {"CLIENTE: \n"+objE.getCliente().toString()};
                  String info3[] = {objE.getMerc().getCod(),objE.getMerc().getDescr(),""+objE.getMerc().getKilos(),objE.getOrigen(),objE.getDestino()};
                  pdf.PDFfactura(info1,info2,info3,objE.getNroE(),""+objE.valorEnvio());
                  envioDAO.insertar(objE,Envio);
                  JOptionPane.showMessageDialog(frmP,"Envio registrado Correctamente!");
                  limpiarVentanaEnv();
                  frmEnv.setVisible(false);    
                } 
            } catch (EnterFormatException ex) {
                JOptionPane.showMessageDialog(frmEnv,"Error: "+ex.getMessage());
            }                                    
         }
        //////////////////////////////////////////////////////////////////////////CRUD         
        if(e.getSource().equals(frmP.getMnuCRUD())){
            frmCli.setVisible(false);
            frmMer.setVisible(false);  
            frmEnv.setVisible(false);  
            frmCrud.setVisible(true); 
            frmCrud.getLblTotal().setText(""+objR.totalEnvios());
        }
        if(e.getSource().equals(frmCrud.getBtnPdfCli())){
            pdf.JtablePDFCliente(frmCrud.getTblCli());
        }
        if(e.getSource().equals(frmCrud.getBtnPdfMerc())){
            pdf.JtablePDFMercancia(frmCrud.getTblMerc());
        }
        if(e.getSource().equals(frmCrud.getBtnPdfEnv())){
            pdf.JtablePDFEnvio(frmCrud.getTblEnvios(),"VALOR ENVIO: "+objR.totalEnvios());
        }        
        //////////////////////////////////////////////////////////////////////////CRUD Cliente
        if(e.getSource().equals(frmCrud.getBtnInsertarCli())){ //Insertar
            frmMer.setVisible(false);  
            frmEnv.setVisible(false);  
            frmCrud.setVisible(true);
            frmCli.setVisible(true);            
        }
        if(e.getSource().equals(frmCrud.getBtnBuscarCli())){//Buscar
            int fila=frmCrud.getTblCli().getSelectedRow();
            String id=frmCrud.getTblCli().getValueAt(fila,0).toString();
            JOptionPane.showMessageDialog(frmP,clienteDAO.buscarDato(id));
        }
        if(e.getSource().equals(frmCrud.getBtnActualizarCli())){//Actualizar
            frmCli.setVisible(true);
            frmMer.setVisible(false);  
            frmEnv.setVisible(false);  
            frmCrud.setVisible(true);
            frmCli.getBtnReg().setText("Actualizar");
            int fila=frmCrud.getTblCli().getSelectedRow();
            String id=frmCrud.getTblCli().getValueAt(fila,0).toString();
            Cliente objC=traerCliente(id);
            frmCli.getTxtId().setText(id);
            frmCli.getTxtId().setEnabled(false);
            frmCli.getTxtNom().setText(objC.getNom());
            frmCli.getTxtTel().setText(objC.getTel());           
        } 
        if(e.getSource().equals(frmCli.getBtnReg())){ 
            if(frmCli.getBtnReg().getText().equals("Actualizar")){
                try {
                    frmCli.getBtnReg().setText("Registrar");
                    Cliente objC = new Cliente();
                    objC.setId(frmCli.getTxtId().getText());
                    objC.setNom(frmCli.getTxtNom().getText());
                    objC.setTel(frmCli.getTxtTel().getText());
                    modificarTablaCli(objC);
                    modificarArrayCli(objC);    
                    modificarCliReporte(objC);
                    if(clienteDAO.actualizar(objC)==1){
                     JOptionPane.showMessageDialog(frmP,"Se ha realizado la actualizacion correctamente");
                    }else{
                        JOptionPane.showMessageDialog(frmP,"Hubo un problema en la actualizacion");
                    }
                    limpiarVentanaCli();
                    frmCli.getTxtId().setEnabled(true);
                    frmCli.setVisible(false);
                } catch (EnterFormatException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(e.getSource().equals(frmCrud.getBtnEliminarCli())){//Eliminar
            int fila=frmCrud.getTblCli().getSelectedRow();
            String id=frmCrud.getTblCli().getValueAt(fila,0).toString();
            if(validarCliEnvio(id)){
                JOptionPane.showMessageDialog(frmP,"No se puede eliminar cliente porque aun se encuentra en un envio");
            }else{
                clienteDAO.eliminar(id);
                eliminarTablaCli(fila,frmCrud.getTblCli());     
                eliminarArrayCli(id);
                JOptionPane.showMessageDialog(frmP,"Se ha eliminado el registro correctamente"); 
            }           
        }
       //////////////////////////////////////////////////////////////////////////CRUD Mercancia
            if(e.getSource().equals(frmCrud.getBtnInsertarMerc())){ //Insertar
                frmCli.setVisible(false);
                frmEnv.setVisible(false);  
                frmCrud.setVisible(true);
                frmMer.setVisible(true); 
            }
            if(e.getSource().equals(frmCrud.getBtnBuscarMerc())){//Buscar
                int fila=frmCrud.getTblMerc().getSelectedRow();
                String codigo=frmCrud.getTblMerc().getValueAt(fila,0).toString();
                JOptionPane.showMessageDialog(frmP,mercanciaDAO.buscarDato(codigo));
            }
            if(e.getSource().equals(frmCrud.getBtnActualizarMerc())){//Actualizar
                frmCli.setVisible(false);
                frmMer.setVisible(true);  
                frmEnv.setVisible(false);  
                frmCrud.setVisible(true);
                frmMer.getBtnReg().setText("Actualizar");
                int fila=frmCrud.getTblMerc().getSelectedRow();
                String codigo=frmCrud.getTblMerc().getValueAt(fila,0).toString();
                Mercancia objM=traerMercancia(codigo);
                frmMer.getTxtCod().setText(codigo);
                frmMer.getTxtCod().setEnabled(false);
                frmMer.getTxtADes().setText(objM.getDescr());
                frmMer.getJspKilos().setValue(objM.getKilos());
            } 
            if(e.getSource().equals(frmMer.getBtnReg())){ 
                if(frmMer.getBtnReg().getText().equals("Actualizar")){
                    try {
                        frmMer.getBtnReg().setText("Registrar");
                        Mercancia objM = new Mercancia();
                        objM.setCod(frmMer.getTxtCod().getText());
                        objM.setDescr(frmMer.getTxtADes().getText());
                        objM.setKilos(Double.parseDouble(frmMer.getJspKilos().getValue().toString()));
                        modificarTablaMerc(objM);
                        modificarArrayMerc(objM);    
                        modificarMercReporte(objM);
                            if(mercanciaDAO.actualizar(objM)==1){
                             JOptionPane.showMessageDialog(frmP,"Se ha realizado la actualizacion correctamente");
                            }else{
                                JOptionPane.showMessageDialog(frmP,"Hubo un problema en la actualizacion");
                            }
                        limpiarVentanaMer();
                        frmMer.getTxtCod().setEnabled(true);
                        frmMer.setVisible(false);  
                    } catch (EnterFormatException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(e.getSource().equals(frmCrud.getBtnEliminarMerc())){//Eliminar
                int fila=frmCrud.getTblMerc().getSelectedRow();
                String codigo=frmCrud.getTblMerc().getValueAt(fila,0).toString();
                if(validarMercEnvio(codigo)){
                    JOptionPane.showMessageDialog(frmP,"No se puede eliminar cliente porque aun se encuentra en un envio");
                }else{
                    mercanciaDAO.eliminar(codigo);
                    eliminarTablaCli(fila,frmCrud.getTblMerc());//Reutilizo codigo de eliminarTablaCli     
                    eliminarArrayMerc(codigo);
                    JOptionPane.showMessageDialog(frmP,"Se ha eliminado el registro correctamente"); 
                }           
            }
        //////////////////////////////////////////////////////////////////////////CRUD ENVIO
        if(e.getSource().equals(frmCrud.getBtnInsertarEnv())){//Insertar
            frmCli.setVisible(false);
            frmMer.setVisible(false);  
            frmEnv.setVisible(true);  
            frmCrud.setVisible(false);  
            frmEnv.getTxtCodMer().setEnabled(false);
            frmEnv.getTxtDestino().setEnabled(false);
            frmEnv.getTxtOrigen().setEnabled(false);
            frmEnv.getCmbEnvio().setEnabled(false);
            frmEnv.getBtnReg().setEnabled(false);
            frmEnv.getBtnBMerc().setEnabled(false);
            frmEnv.getTxtKilos().setEnabled(false);
            frmEnv.getBtnVKilos().setEnabled(false);
            frmCrud.setVisible(true);
        }
        if(e.getSource().equals(frmCrud.getBtnBuscarEnv())){//Buscar
                int fila=frmCrud.getTblEnvios().getSelectedRow();
                String Nro=frmCrud.getTblEnvios().getValueAt(fila,0).toString();
                JOptionPane.showMessageDialog(frmP,envioDAO.buscarDato(Nro));
        }
        if(e.getSource().equals(frmCrud.getBtnActualizarEnv())){//Actualizar
                frmCli.setVisible(false);
                frmMer.setVisible(false);  
                frmEnv.setVisible(true);  
                frmCrud.setVisible(true);
                frmEnv.getBtnReg().setText("Actualizar");
                int fila=frmCrud.getTblEnvios().getSelectedRow();
                String Nro=frmCrud.getTblEnvios().getValueAt(fila,0).toString();
                Envio objE=traerEnvio(Nro);
                frmEnv.getLblNro().setText(objE.getNroE());
                frmEnv.getTxtIdCli().setText(objE.getCliente().getId());
                frmEnv.getTxtCodMer().setText(objE.getMerc().getCod());
                frmEnv.getTxtKilos().setText(String.valueOf(objE.getMerc().getKilos()));
                if(objE.getClass().getName().equals("Aereo")){
                    frmEnv.getCmbEnvio().setSelectedIndex(1);
                }else{
                    frmEnv.getCmbEnvio().setSelectedIndex(2);
                }
                frmEnv.getTxtOrigen().setText(objE.getOrigen());
                frmEnv.getTxtDestino().setText(objE.getDestino());              
            } 
            if(e.getSource().equals(frmEnv.getBtnReg())){ 
                if(frmEnv.getBtnReg().getText().equals("Actualizar")){
                    try {
                        frmEnv.getBtnReg().setText("Registrar");
                        Envio objE = null;
                        if(frmEnv.getCmbEnvio().getSelectedIndex()==1){
                           objE=new Aereo();
                        }else{
                            objE=new Terrestre();
                        }
                        objE.setNroE(frmEnv.getLblNro().getText());
                        objE.setCliente(traerCliente(frmEnv.getTxtIdCli().getText()));
                        objE.setMerc(traerMercancia(frmEnv.getTxtCodMer().getText()));
                        objE.setDestino(frmEnv.getTxtDestino().getText());
                        objE.setOrigen(frmEnv.getTxtOrigen().getText());                       
                        modificarTablaEnv(objE);
                        modificarArrayEnv(objE);    
                            if(envioDAO.actualizar(objE)==1){
                             JOptionPane.showMessageDialog(frmP,"Se ha realizado la actualizacion correctamente");
                            }else{
                                JOptionPane.showMessageDialog(frmP,"Hubo un problema en la actualizacion");
                            }
                        limpiarVentanaEnv();
                        frmEnv.setVisible(false);  
                    } catch (EnterFormatException ex) {
                        Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(e.getSource().equals(frmCrud.getBtnEliminarEnv())){//Eliminar
                int fila=frmCrud.getTblEnvios().getSelectedRow();
                String Nro=frmCrud.getTblEnvios().getValueAt(fila,0).toString();
                    envioDAO.eliminar(Nro);
                    eliminarTablaCli(fila,frmCrud.getTblEnvios());//Reutilizo codigo de eliminarTablaCli     
                    eliminarArrayEnv(Nro);
                    JOptionPane.showMessageDialog(frmP,"Se ha eliminado el registro correctamente"); 
                           
            }
        //////////////////////////////////////////////////////////////////////////ACERCA
        if(e.getSource().equals(frmP.getMnuAcerca())){
            frmCli.setVisible(false);
            frmMer.setVisible(false);  
            frmEnv.setVisible(false);  
            frmCrud.setVisible(false);
            frmAcer.setVisible(true);
        }
        //////////////////////////////////////////////////////////////////////////SALIR
        if(e.getSource().equals(frmP.getMnuSalir())){
            int Resp=JOptionPane.showConfirmDialog(frmP, "¿Desea Salir?");
            if(Resp==JOptionPane.YES_OPTION){
                System.exit(1);
            }
        } 
    }
    //////////////////////////////////////////////////////////////////////////HILOS    
    @Override
    public void run() {
        while(true){
            frmP.getLblFechayHora().setText(objH.toString());
            objH.incSS();
            try{ 
                Thread.sleep(1000);
            }catch(InterruptedException ex){
                JOptionPane.showMessageDialog(frmP, ex);
            }
       }
    }
    //////////////////////////////////////////////////////////    
    public void cargarCli(){
        DefaultTableModel plantilla = (DefaultTableModel) frmCrud.getTblCli().getModel();
        for(int x=0;x<plantilla.getRowCount();x++){
            Cliente objC=new Cliente(plantilla.getValueAt(x,0).toString().replace(" ",""),plantilla.getValueAt(x,1).toString().replace(" ",""),plantilla.getValueAt(x,2).toString().replace(" ",""));
            arrayC.add(objC);
        }
    }
    public void cargarMerc(){
        DefaultTableModel plantilla = (DefaultTableModel) frmCrud.getTblMerc().getModel();
        for(int x=0;x<plantilla.getRowCount();x++){
            Mercancia objM=new Mercancia(plantilla.getValueAt(x,0).toString().replace(" ",""),plantilla.getValueAt(x,1).toString().replace(" ",""),Double.parseDouble(plantilla.getValueAt(x,2).toString().replace(" ","")));
            arrayM.add(objM);
        }
    }
    public void cargarEnv(){
        try {
            DefaultTableModel plantilla = (DefaultTableModel) frmCrud.getTblEnvios().getModel();
            for(int x=0;x<plantilla.getRowCount();x++){
                Envio objE=null;
                if(plantilla.getValueAt(x,1).equals("Aereo")){
                    objE=new Aereo();
                }else{
                    objE=new Terrestre();
                }
                 objE.setNroE(plantilla.getValueAt(x,0).toString().replace(" ",""));
                 objE.setOrigen(plantilla.getValueAt(x,7).toString().replace(" ",""));
                 objE.setDestino(plantilla.getValueAt(x,8).toString().replace(" ",""));
                 objE.setCliente(traerCliente(plantilla.getValueAt(x,2).toString().replace(" ","")));
                 
                 objE.setMerc(traerMercancia(plantilla.getValueAt(x,4).toString().replace(" ","")));                
                 objR.getListaE().add(objE);
            }
        } catch (EnterFormatException ex) {
                JOptionPane.showMessageDialog(frmP, ex.getMessage());
        }
    }
    public void envioTablaEnv(Envio objE, JTable tabla,String Envio){
        DefaultTableModel plantilla = (DefaultTableModel) tabla.getModel();
        plantilla.addRow(objE.datos(Envio));
    }
    
    public void envioTablaCli(Cliente objC, JTable tabla){
        DefaultTableModel plantilla = (DefaultTableModel) tabla.getModel();
        plantilla.addRow(objC.datos());
    } 
    
    public void envioTablaMerc(Mercancia objM, JTable tabla){
        DefaultTableModel plantilla = (DefaultTableModel) tabla.getModel();
        plantilla.addRow(objM.datos());
    } 
    
    public boolean buscarCli(String id){
        boolean band=false;
        for(int x=0;x<arrayC.size();x++){
            if(arrayC.get(x).getId().equals(id)){
                band=true;
            }
        }
        return band;
    }
    
    public boolean buscarMerc(String Codigo){
        boolean band=false;
        for(int x=0;x<arrayM.size();x++){
            if(arrayM.get(x).getCod().equals(Codigo)){
                band=true;
            }
        }
        return band;
    }
    
    public Cliente traerCliente(String id){
        Cliente objC=null;      
        for(int x=0;x<arrayC.size();x++){  
            if(arrayC.get(x).getId().equals(id)){
                objC=arrayC.get(x);
            }
        }
        return objC;
    }
    
    public Mercancia traerMercancia(String Codigo){
        Mercancia objM=null;
        for(int x=0;x<arrayM.size();x++){
            if(arrayM.get(x).getCod().equals(Codigo)){
                objM=arrayM.get(x);
            }
        }
        return objM;
    }
    
    public void limpiarVentanaCli(){
        frmCli.getTxtId().setText("");
        frmCli.getTxtNom().setText("");
        frmCli.getTxtTel().setText("");
    }
    
    public void limpiarVentanaMer(){
        frmMer.getTxtADes().setText("");
        frmMer.getTxtCod().setText("");
        frmMer.getJspKilos().setValue(0);
    }
    
    public void limpiarVentanaEnv(){
        frmEnv.getTxtCodMer().setText("");
        frmEnv.getTxtIdCli().setText("");
        frmEnv.getTxtDestino().setText("");
        frmEnv.getTxtOrigen().setText("");
        frmEnv.getTxtKilos().setText("");
        frmEnv.getLblNro().setText("");
   }
    
   public boolean validarKilos(double kilos){
       boolean band = false;
       for(int x=0;x<arrayM.size();x++){
           if(kilos<=arrayM.get(x).getKilos()){
               band=true;
           }
       }
       return band;
   }
   public void restarMerca(double kilos,String Cod){
       for(int x=0;x<arrayM.size();x++){
           if(arrayM.get(x).getCod().equals(Cod)){
               try {
                   arrayM.get(x).setKilos(arrayM.get(x).getKilos()-kilos);
               } catch (EnterFormatException ex) {
                  JOptionPane.showMessageDialog(frmEnv,"Error: "+ex.getMessage()); 
               }
           }
       }
   }
   public void validarID(String id) throws EnterFormatException{
      for(int x=0;x<arrayC.size();x++){
          if(arrayC.get(x).getId().equals(id)){
              throw new EnterFormatException(3);
          }
      }
   }

   public void modificarTablaCli(Cliente objC){
       for(int x=0;x<frmCrud.getTblCli().getRowCount();x++){
           if(frmCrud.getTblCli().getValueAt(x,0).toString().equals(objC.getId())){
               frmCrud.getTblCli().setValueAt(objC.getNom(),x,1);
               frmCrud.getTblCli().setValueAt(objC.getTel(),x,2);
           }
       }
   }
   public void modificarArrayCli(Cliente objC){
       try {
            for(int x=0;x<arrayC.size();x++){
                if(arrayC.get(x).getId().equals(objC.getId())){
                    arrayC.get(x).setNom(objC.getNom());
                    arrayC.get(x).setTel(objC.getTel());
                }
            }
       } catch (EnterFormatException ex) {
         JOptionPane.showMessageDialog(frmP,ex.getMessage());
       } 
   }
   
   public void modificarCliReporte(Cliente nuevoCli){
       for(int x=0;x<objR.getListaE().size();x++){
           if(objR.getListaE().get(x).getCliente().getId().equals(nuevoCli.getId())){
               objR.getListaE().get(x).setCliente(nuevoCli);
           }
        }
       
   }
   public boolean validarCliEnvio(String id){
       boolean band=false;
       for(int x=0;x<objR.getListaE().size();x++){
           if(objR.getListaE().get(x).getCliente().getId().equals(id)){
               band=true;
           }
        }
       return band;
   }
   public void eliminarTablaCli(int fila,JTable tabla){
       DefaultTableModel plantilla = (DefaultTableModel)tabla.getModel();
       plantilla.removeRow(fila);
   }
   public void eliminarArrayCli(String id){
       for(int x=0;x<arrayC.size();x++){
           if(arrayC.get(x).getId().equals(id)){
               arrayC.remove(x);
           }
       }
   }
    public void modificarTablaMerc(Mercancia objM){
       for(int x=0;x<frmCrud.getTblMerc().getRowCount();x++){
           if(frmCrud.getTblMerc().getValueAt(x,0).toString().equals(objM.getCod())){
               frmCrud.getTblMerc().setValueAt(objM.getDescr(),x,1);
               frmCrud.getTblMerc().setValueAt(objM.getKilos(),x,2);
           }
       }
   }
   public void modificarArrayMerc(Mercancia objM){
       try {
            for(int x=0;x<arrayM.size();x++){
                if(arrayM.get(x).getCod().equals(objM.getCod())){
                    arrayM.get(x).setDescr(objM.getDescr());
                    arrayM.get(x).setKilos(objM.getKilos());
                }
            }
       } catch (EnterFormatException ex) {
         JOptionPane.showMessageDialog(frmP,ex.getMessage());
       } 
   }
   
   public void modificarMercReporte(Mercancia nuevoMerc){
       for(int x=0;x<objR.getListaE().size();x++){
           if(objR.getListaE().get(x).getMerc().getCod().equals(nuevoMerc.getCod())){
               objR.getListaE().get(x).setMerc(nuevoMerc);
           }
        }
       
   }
   public boolean validarMercEnvio(String codigo){
       boolean band=false;
       for(int x=0;x<objR.getListaE().size();x++){
           if(objR.getListaE().get(x).getMerc().getCod().equals(codigo)){
               band=true;
           }
        }
       return band;
   }
   
   public void eliminarArrayMerc(String codigo){
       for(int x=0;x<arrayM.size();x++){
           if(arrayM.get(x).getCod().equals(codigo)){
               arrayM.remove(x);
           }
       }
   }
   public Envio traerEnvio(String Nro){
       Envio objE = null;
       for(int x=0;x<objR.getListaE().size();x++){
           if(objR.getListaE().get(x).getNroE().equals(Nro)){
               objE=objR.getListaE().get(x);
               
           }
       }
       return objE;
   }
   public void modificarTablaEnv(Envio objE){
       for(int x=0;x<frmCrud.getTblEnvios().getRowCount();x++){
           if(frmCrud.getTblEnvios().getValueAt(x,0).toString().equals(objE.getNroE())){
               if(objE.getClass().getName().equals("Aereo")){
                   frmCrud.getTblEnvios().setValueAt("Aereo",x,1);
               }else{
                   frmCrud.getTblEnvios().setValueAt("Terrestre",x,1);
               }              
               frmCrud.getTblEnvios().setValueAt(objE.getCliente().getId(),x,2);
               frmCrud.getTblEnvios().setValueAt(objE.getCliente().getNom(),x,3);
               frmCrud.getTblEnvios().setValueAt(objE.getMerc().getCod(),x,4);
               frmCrud.getTblEnvios().setValueAt(objE.getMerc().getDescr(),x,5);
               frmCrud.getTblEnvios().setValueAt(objE.getMerc().getKilos(),x,6);
               frmCrud.getTblEnvios().setValueAt(objE.getOrigen(),x,7);
               frmCrud.getTblEnvios().setValueAt(objE.getDestino(),x,8);
               frmCrud.getTblEnvios().setValueAt(objE.valorEnvio(),x,9);
           }
       }
   }
   public void modificarArrayEnv(Envio objE){
       try {
            for(int x=0;x<objR.getListaE().size();x++){
                if(objR.getListaE().get(x).getNroE().equals(objE.getNroE())){
                    objR.getListaE().get(x).setCliente(objE.getCliente());
                    objR.getListaE().get(x).setMerc(objE.getMerc());
                    objR.getListaE().get(x).setNroE(objE.getNroE());
                    objR.getListaE().get(x).setOrigen(objE.getOrigen());
                    objR.getListaE().get(x).setDestino(objE.getDestino());
                }
            }
       } catch (EnterFormatException ex) {
         JOptionPane.showMessageDialog(frmP,ex.getMessage());
       } 
   }
   public void eliminarArrayEnv(String Nro){
       for(int x=0;x<objR.getListaE().size();x++){
           if(objR.getListaE().get(x).getNroE().equals(Nro)){
               objR.getListaE().remove(x);
           }
       }
   }
}
