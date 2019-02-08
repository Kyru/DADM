package fyq.example.labdadm.labs.QuotationMethods;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fyq.example.labdadm.labs.Quotation;
import fyq.example.labdadm.labs.R;

public class QuotationArrayAdapter extends ArrayAdapter {

    private int layout;

    public QuotationArrayAdapter(Context context, int layout, List<Quotation> quotationList){
        super(context, layout, quotationList);
        this.layout = layout;

    }

    static class ViewHolder {

        private TextView tv_quoteAuthor;
        private TextView tv_quoteText;

        public ViewHolder(TextView tv_quoteText, TextView tv_quoteAuthor) {
            this.tv_quoteAuthor = tv_quoteAuthor;
            this.tv_quoteText = tv_quoteText;
        }

        public TextView getTv_quoteAuthor() {
            return tv_quoteAuthor;
        }

        public void setTv_quoteAuthor(TextView tv_quoteAuthor) {
            this.tv_quoteAuthor = tv_quoteAuthor;
        }

        public TextView getTv_quoteText() {
            return tv_quoteText;
        }

        public void setTv_quoteText(TextView tv_quoteText) {
            this.tv_quoteText = tv_quoteText;
        }
    }


 @Override
 public View getView(int position, View view, ViewGroup parent){

        if(view == null){
            LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();
            view = (layoutInflater.inflate(this.layout, null));

            ViewHolder viewHolder = new ViewHolder((TextView) view.findViewById(R.id.tv_quoteText),
                    (TextView) view.findViewById(R.id.tv_quoteAuthor));

            view.setTag(viewHolder);
        }

        ViewHolder resViewHolder = (ViewHolder) view.getTag();
        TextView tv_quoteAuthor = resViewHolder.getTv_quoteAuthor();
        TextView tv_quoteText = resViewHolder.getTv_quoteText();

        Quotation quotation = (Quotation) getItem(position);
        tv_quoteAuthor.setText(quotation.getQuoteAuthor());
        tv_quoteText.setText(quotation.getQuoteText());

        return view;


    }

}

