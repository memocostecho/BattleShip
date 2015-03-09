package mx.unam.computomovil.battleship;

/**
 * Created by yasminegutierrez on 1/6/15.
 */




        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;



public class ScoreCustomAdapter extends ArrayAdapter<Score> {

    public ScoreCustomAdapter(Context context, ArrayList<Score> items) {
        super(context, 0, items);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.scoreline, parent, false);
        TextView scoreTV = (TextView) rowView.findViewById(R.id.score);
        TextView nombreTV = (TextView) rowView.findViewById(R.id.nombreScore);
      //  ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        Score sc = getItem(position);
        scoreTV.setText(sc.getScore());
        nombreTV.setText(sc.getNombre());

        return rowView;
    }
}
