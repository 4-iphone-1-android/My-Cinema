package com.example.mycinema;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder>{

    private Context mContext;
    private List<BookActivity> mData;

    public TicketAdapter(Context mContext, List<BookActivity> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ticket_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookActivity ticket = mData.get(position);
        holder.tvName.setText(ticket.getName());
        holder.tvDate.setText(ticket.getDate());
        holder.tvTime.setText(ticket.getTime());
        holder.tvSeat.setText(ticket.getSeat());
        byte[] decodedString = android.util.Base64.decode(ticket.getImage(), android.util.Base64.DEFAULT);

        Glide.with(mContext)
                .load(decodedString)
                .into(holder.imgFilm);
        holder.imgQRCode.setImageBitmap(createQRCode(ticket.getCodeQR()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private Bitmap createQRCode(String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text,
                    BarcodeFormat.QR_CODE, 600, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvTime, tvSeat;
        ImageView imgFilm, imgQRCode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.ticket_name);
            tvDate = itemView.findViewById(R.id.ticket_date);
            tvTime = itemView.findViewById(R.id.ticket_time);
            tvSeat = itemView.findViewById(R.id.ticket_seat);
            imgFilm = itemView.findViewById(R.id.ticket_image);
            imgQRCode = itemView.findViewById(R.id.ticket_QR);
        }
    }
}
