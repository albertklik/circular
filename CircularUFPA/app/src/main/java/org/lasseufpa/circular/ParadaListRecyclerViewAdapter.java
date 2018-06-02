package org.lasseufpa.circular;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.lasseufpa.circular.Domain.Parada;
import org.lasseufpa.circular.Domain.ParadasList;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by alberto on 03/09/2017.
 */

public class ParadaListRecyclerViewAdapter extends RecyclerView.Adapter<ParadaListRecyclerViewAdapter.ViewHolder> {


    private ArrayList<Parada> paradas;

    private static final int INICIO_IDA = 1;
    private static final int FIM_IDA = 2;
    private static final int NORMAL_IDA = 3;
    private static final int INICIO_VOLTA = 4;
    private static final int FIM_VOLTA = 5;
    private static final int NORMAL_VOLTA = 6;



    public ParadaListRecyclerViewAdapter(ArrayList<Parada> paradas) {
        this.paradas = paradas;
    }

    @Override
    public ParadaListRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_paradaslist_inicio_ida, parent, false);
            break;
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_para_list_fim_ida, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_parada_list_ida, parent, false);
                break;
            case 4:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_parada_list_inicio_volta, parent, false);
                break;
            case 5:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_parada_list_fim_volta, parent, false);
                break;
            case 6:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_parada_list_volta, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_parada_list_ida, parent, false);
                break;
        }

        return new ParadaListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Parada p = paradas.get(position);
        if (p.getTipo()==ParadasList.TIPO_INICIO&&p.getSentido_rota()==ParadasList.ROTA_IDA) return INICIO_IDA;
        else if (p.getTipo()==ParadasList.TIPO_FINAL&&p.getSentido_rota()==ParadasList.ROTA_IDA) return FIM_IDA;
        else if (p.getTipo()==ParadasList.TIPO_PARDA&&p.getSentido_rota()==ParadasList.ROTA_IDA) return NORMAL_IDA;
        else if (p.getTipo()==ParadasList.TIPO_INICIO&&p.getSentido_rota()==ParadasList.ROTA_VOLTA) return INICIO_VOLTA;
        else if (p.getTipo()==ParadasList.TIPO_FINAL&&p.getSentido_rota()==ParadasList.ROTA_VOLTA) return FIM_VOLTA;
        else if (p.getTipo()==ParadasList.TIPO_PARDA&&p.getSentido_rota()==ParadasList.ROTA_VOLTA) return NORMAL_VOLTA;
        else return 0;
    }




    @Override
    public void onBindViewHolder(final ParadaListRecyclerViewAdapter.ViewHolder holder, int position) {
        Parada p = paradas.get(position);
        holder.mCircularName = p.getTitle();
        holder.mIdView.setText(p.getTitle());
        holder.mContentView.setText(p.getDescription());



    }



    @Override
    public int getItemCount() {
        return paradas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mIdView;
        private final TextView mContentView;
        private final ImageView mImageView;
        private final View lineView;
        private final CircleImageView circImage;
        private Parada p;
        private String mCircularName;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mIdView = (TextView) view.findViewById(R.id.tituloParada);
            mContentView = (TextView) view.findViewById(R.id.content);
            mImageView = (ImageView) view.findViewById(R.id.stopicon);
            //mCardView = (CardView) view.findViewById(R.id.card_view);
            lineView = (View) view.findViewById(R.id.ViewLine);
            circImage = (CircleImageView) view.findViewById(R.id.circimg);

        }

    }
}
