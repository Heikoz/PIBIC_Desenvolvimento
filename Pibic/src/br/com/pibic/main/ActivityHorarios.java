package br.com.pibic.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.TextView;
import br.com.ufac.bean.Aluno;
import br.com.ufac.bean.Horario;
 
public class ActivityHorarios extends Activity {
	public static final String ACAO_EXIBIR_HORARIOS = "main.ACAO_EXIBIR_HORARIOS";
	public static final String CATEGORIA_HORARIOS = "main.CATEGORIA_HORARIOS";
	public static final String HORARIO = "main.HORARIO";
	
    private ExpandableListAdapter listAdapter;
    private ExpandableListView expListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private Horario horarios;
    private TextView tvSaudacao;
    private Aluno aluno;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_horarios);
        Intent intent = getIntent();
        horarios = (Horario) intent.getSerializableExtra(HORARIO);
        aluno = (Aluno) intent.getSerializableExtra(ActivityMenu.ALUNO);
		
		tvSaudacao = (TextView) findViewById(R.id.txtSaudacao);
		tvSaudacao.setText("Olá, "+ aluno.getFirstName());
		Log.i("ActivityHorarios", horarios.toString());
		
		// get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
 
        // preparing list data
        prepareListData();
 
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
 
        // setting list adapter
        expListView.setAdapter(listAdapter);
    }
 
    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("Segunda-feira:");
        listDataHeader.add("Terça-feira");
        listDataHeader.add("Quarta-feira");
        listDataHeader.add("Quinta-feira");
        listDataHeader.add("Sexta-feira");
        listDataHeader.add("Sábado");
 
        int aux = -1;
        List<String> tempo = horarios.getTempo();
        // Adding child data
        List<String> segunda = new ArrayList<String>();
        for (String g : horarios.getSegunda()){
        	aux++;
        	if (g.equals(""))
        			continue;
        	segunda.add(tempo.get(aux)+": "+g);
        }
        
        aux = -1;
        List<String> terca = new ArrayList<String>();
        for (String g : horarios.getTerca()){
        	aux++;
        	if (g.equals(""))
        			continue;
        	terca.add(tempo.get(aux)+": "+g);
        	
        }
        
        aux = -1;
        List<String> quarta = new ArrayList<String>();
        for (String g : horarios.getQuarta()){
        	aux++;
        	if (g.equals(""))
        			continue;
        	quarta.add(tempo.get(aux)+": "+g);
        }
        
        aux = -1;
        List<String> quinta = new ArrayList<String>();
        for (String g : horarios.getQuinta()){
        	aux++;
        	if (g.equals(""))
        			continue;
        	quinta.add(tempo.get(aux)+": "+g);
        }
        
        aux = -1;
        List<String> sexta = new ArrayList<String>();
        for (String g : horarios.getSexta()){
        	aux++;
        	if (g.equals(""))
        			continue;
        	sexta.add(tempo.get(aux)+": "+g);
        }
        
        aux = -1;
        List<String> sabado = new ArrayList<String>();
        for (String g : horarios.getSabado()){
        	aux++;
        	if (g.equals(""))
        			continue;
        	terca.add(tempo.get(aux)+": "+g);
        }

        listDataChild.put(listDataHeader.get(0), segunda); // Header, Child data
        listDataChild.put(listDataHeader.get(1), terca); // Header, Child data
        listDataChild.put(listDataHeader.get(2), quarta); // Header, Child data
        listDataChild.put(listDataHeader.get(3), quinta); // Header, Child data
        listDataChild.put(listDataHeader.get(4), sexta); // Header, Child data
        listDataChild.put(listDataHeader.get(5), sabado); // Header, Child data
    }
}