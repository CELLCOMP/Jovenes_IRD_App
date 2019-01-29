package com.example.gamero.youth_ird;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador_feeds extends RecyclerView.Adapter<Adaptador_feeds.MyViewHolder> {
    private Context context;
    private ArrayList<FeedsVo> lista_feeds;
    View.OnClickListener mlistener;

    public Adaptador_feeds(Context context, ArrayList<FeedsVo> lista_feeds) {
        this.context = context;
        this.lista_feeds = lista_feeds;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista_feeds, null, false);
        return new MyViewHolder(view, context, lista_feeds);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.titulo.setText(lista_feeds.get(i).getTitulo());
        myViewHolder.comentario.setText(lista_feeds.get(i).getMensaje());
        // myViewHolder.imagen.setImageResource(Integer.parseInt(lista_feeds.get(i).getImagen()));
        myViewHolder.tiempo.setText(lista_feeds.get(i).getCreate_at());


        myViewHolder.button_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.edit_popup:
                                Toast.makeText(context, "Editar", Toast.LENGTH_SHORT).show();
                                editarPublicacion();
                                break;

                            case R.id.compartir_popup:
                                Toast.makeText(context, "Compartir", Toast.LENGTH_LONG).show();
                                compartirPublicacion();
                                break;

                            case R.id.eliminar_popup:
                                Toast.makeText(context, "Eliminar", Toast.LENGTH_SHORT).show();
                                eliminarPublicacion();
                                break;
                            default:
                                break;
                        }
                        return true;


                    }

                });

                popupMenu.show();
            }

        });
    }

    private void compartirPublicacion() {


    }

    private void eliminarPublicacion() {
    }

    private void editarPublicacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage("¿Quiere editar la publicacion?");

        builder.setTitle("Confirmación");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(context,Editar_Publicacion_Activity.class);
                Activity activity = (Activity)context;
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_bottom);

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();

    }

    @Override
    public int getItemCount() {
        return lista_feeds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, tiempo, comentario;
        ImageView imagen;
        Context context;
        View itemView;
        ArrayList<FeedsVo> lista_feeds;
        ImageButton button_options;

        public MyViewHolder(@NonNull View itemView, Context context, ArrayList<FeedsVo> lista_feeds) {
            super(itemView);
            this.lista_feeds = lista_feeds;
            this.itemView = itemView;
            this.context = context;

            titulo = itemView.findViewById(R.id.titulo_publicacion);
            tiempo = itemView.findViewById(R.id.tiempo_publicacion);
            imagen = itemView.findViewById(R.id.imagen_publicacion);
            comentario = itemView.findViewById(R.id.comentario_publicacion);
            button_options = itemView.findViewById(R.id.menu_publicacion);
        }


    }
}
