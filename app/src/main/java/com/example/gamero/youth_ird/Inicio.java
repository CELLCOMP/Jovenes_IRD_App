package com.example.gamero.youth_ird;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Inicio.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Inicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inicio extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Handler handler;
    private Runnable runnable;
    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private Adaptador_feeds adaptador_feeds;
    private ArrayList<FeedsVo> lista_feeds;
    private RelativeLayout relativeLayoutTimer;
    private CardView cardViewCronometro;
    Context context;
    private ImageButton botonCerrar;
    private JsonObjectRequest jsonObjectRequest;
    private TextView dias, horas,minutos, segundos,mensajeTimer,tituloCronometro;

    public Inicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static Inicio newInstance(String param1, String param2) {
        Inicio fragment = new Inicio();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_inicio, container, false);


        botonCerrar = view.findViewById(R.id.cerrarCronometroButton);
        tituloCronometro = view.findViewById(R.id.titulo_cronometro);
        cardViewCronometro =view.findViewById(R.id.CV1);
        horas = view.findViewById(R.id.horas);
        dias = view.findViewById(R.id.dias);
        minutos = view.findViewById(R.id.minutos);
        segundos = view.findViewById(R.id.segundos);
        mensajeTimer = view.findViewById(R.id.mensajeTimer);
        relativeLayoutTimer = view.findViewById(R.id.relativeTime);

        countDownTimer();

        recyclerView = view.findViewById(R.id.recyclerview_inicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        lista_feeds = new ArrayList<>();
        cargarPublicaciones();
        adaptador_feeds = new Adaptador_feeds(context, lista_feeds);
        recyclerView.setAdapter(adaptador_feeds);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        botonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewCronometro.setVisibility(View.GONE);

            }
        });



        return view;
    }

    private void countDownTimer() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    relativeLayoutTimer.setVisibility(View.VISIBLE);
                    String futureDate1 =obtainNextFriday(obtenerDia_de_la_semana());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    // Please here set your event date//YYYY-MM-DD
                    Date futureDate = dateFormat.parse(futureDate1);
                    Date currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        long diff = futureDate.getTime()
                                - currentDate.getTime();
                        long days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        long hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        long minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        long seconds = diff / 1000;
                        dias.setText("" + String.format("%02d", days));
                        horas.setText("" + String.format("%02d", hours));
                        minutos.setText(""
                                + String.format("%02d", minutes));
                        segundos.setText(""
                                + String.format("%02d", seconds));
                    } else {
                        mensajeTimer.setVisibility(View.VISIBLE);
                        tituloCronometro.setVisibility(View.GONE);
                        mensajeTimer.setText("Hoy tenemos reunion de Jovenes");
                        textViewGone();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private  String obtainNextFriday(int day) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.DAY_OF_YEAR,  day);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    private void textViewGone() {
        relativeLayoutTimer.setVisibility(View.GONE);

    }


    private  int obtenerDia_de_la_semana(){
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_WEEK);
        int dias_faltantes;
        switch (dia){
            case Calendar.SUNDAY:
                dias_faltantes =5;
                break;
            case Calendar.MONDAY:
                dias_faltantes =4;
                break;
            case Calendar.TUESDAY:

                dias_faltantes =3;
                break;
            case Calendar.WEDNESDAY:

                dias_faltantes =2;
                break;
            case Calendar.THURSDAY:
                dias_faltantes =1;
                break;
            case  Calendar.FRIDAY:
                dias_faltantes = 0;
                break;
            case Calendar.SATURDAY:

                dias_faltantes =6;
                break;
            default:
                dias_faltantes =0;
                break;
        }
        return dias_faltantes;

    }

    public void cargarPublicaciones() {
        ProgressDialog progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();

        String ip = getString(R.string.ip);
        String url = ip + "/api/publicaciones";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                lista_feeds = new ArrayList<>();
                FeedsVo feedsVo = null;
                JSONArray array = response.optJSONArray("publicaciones");

                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        try {
                            feedsVo = new FeedsVo();
                            JSONObject obj = null;
                            obj = array.getJSONObject(i);

                            feedsVo.setId_publicacion("id");
                            feedsVo.setTitulo(obj.getString("titulo"));
                            feedsVo.setCreate_at(obj.getString("created_at"));
                            feedsVo.setMensaje(obj.getString("mensaje"));
                            feedsVo.setUsuario(String.valueOf(obj.getInt("id_usuario")));
                            feedsVo.setImagen(obj.getString("imagen"));

                            lista_feeds.add(feedsVo);
                            adaptador_feeds = new Adaptador_feeds(context, lista_feeds);
                            recyclerView.setAdapter(adaptador_feeds);
                            adaptador_feeds.notifyDataSetChanged();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                }else {

                    Toast.makeText(context, "No hay publicaciones aún", Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error al conectar", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3000,2,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
        progreso.dismiss();
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            this.context = context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
