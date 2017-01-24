/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di_ud3_sesion3ireport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

public class Main implements JRDataSource{

    private List<Participantes> listaParticipantes = new ArrayList<>();
    private int indiceParticipanteActual = -1;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        
        Main datasource = new Main();
        
        for (int i = 1; i<= 10; i++) {
            
            Participantes p = new Participantes(i, "Participante " + i, "Usuario "+ i, "Pass " + i, "Comentarios para "+i);
            p.setPuntos(i);
            datasource.addParticipante(p);
        
        } 
        
        try{
            
            JasperReport reporte = (JasperReport) JRLoader.loadObjectFromFile("report4.jasper");
            
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("autor", "Álvaro Giner");
            parametros.put("titulo", "Reporte Participantes");
 
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametros, datasource);
            Exporter exporter = new JRPdfExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new java.io.File("reporteSesion2PDF.pdf")));
            SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
            exporter.setConfiguration(configuration);

            exporter.exportReport();    
        
        } 
        
        catch (JRException ex) {

            ex.printStackTrace();
            
        }
                
    }   

    @Override
    public boolean next() throws JRException {
        
        return ++indiceParticipanteActual<listaParticipantes.size();
        
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        
        Object valor = null;
        
        if("nombre".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getNombre();
        }
        else if("username".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getUsername(); 
        }
        else if("password".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getPassword();
        }
        else if("comentarios".equals(jrf.getName())) {
            valor = listaParticipantes.get(indiceParticipanteActual).getComentarios();
        }
        else if("id".equals(jrf.getName())){
           valor = listaParticipantes.get(indiceParticipanteActual).getId();
        }
        else if("puntos".equals(jrf.getName())){
            valor = listaParticipantes.get(indiceParticipanteActual).getPuntos();
        }
        
        return valor;
        
    }
    
    public void addParticipante(Participantes participante){
        
        this.listaParticipantes.add(participante);
        
    }
    
}
