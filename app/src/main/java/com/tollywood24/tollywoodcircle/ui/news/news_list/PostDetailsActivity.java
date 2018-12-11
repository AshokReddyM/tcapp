package com.tollywood24.tollywoodcircle.ui.news.news_list;

import android.app.Dialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tollywood24.tollywoodcircle.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostDetailsActivity extends AppCompatActivity {


    Dialog dialog;

    @BindView(R.id.web_page_loading_progress)
    ProgressBar progressBar;

    @BindView(R.id.post_heading_toolbar)
    TextView title;

    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.post_img)
    ImageView postImg;

    String desc;
    private String link;
    private String mPostLink;
    private String mPostTitle;
    private CustomTabsIntent.Builder builder;
    private CustomTabsIntent customTabsIntent;
    private String mImageLink;
    private String mDesc;
    private String tag;
    private boolean mCustomTabsOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_web_view);

        ButterKnife.bind(this);
        new Task1().execute();


/*
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                    whatsappIntent.setType("text/plain");
                    whatsappIntent.setPackage("com.whatsapp");
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, mPostTitle);
                    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "*" + mPostTitle + "*" + "\n\n" + content.getText().toString().substring(0, Math.min(content.getText().toString().length(), 500)) + "\n\n" + "*" + "Read Full Post Below Link" + "*" + "\n\n" + "https://goo.gl/zmjcgS" + "\n\n" + "");
                    try {
                        startActivity(whatsappIntent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(PostDetailsActivity.this, "Whatsapp is not installed in your mobile", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
*/
    }

    class Task1 extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar progressBar = new ProgressBar(PostDetailsActivity.this);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Object... params) {

            Elements mainContent = null;


            try {
                if (link.equals(" www.koradanews.com")) {

                    String[] id = setJsoupModal(link);
                    Document doc = Jsoup.connect(mPostLink).get();

                    Element contentData = doc.getElementById(id[0]);

                    contentData.lastElementSibling();
                    if (!id[1].equals("")) {
                        mainContent = contentData.select(id[1]);
                    } else {
                        mainContent = contentData.getAllElements();
                    }


                } else {
                    String[] id = setJsoupModal(link);
                    Document doc = Jsoup.connect(mPostLink).get();
                    Elements contentData = doc.getElementsByClass(id[0]);
                    if (!id[1].equals("")) {
                        mainContent = contentData.select(id[1]);
                    } else {
                        mainContent = contentData;
                    }
                }


            } catch (IOException | NullPointerException e) {
                e.printStackTrace();

            }
            if (mainContent != null) {
                return mainContent.toString();
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            String html = (String) o;
            if (html != null) {
                html = html.replace("<br>", "\n");
                html = html.replace("&nbsp;", "");
                html = html.replace("comments", "");
                content.setText(Html.fromHtml(html.replaceAll("<img.+?>", "")));
                content.setMovementMethod(LinkMovementMethod.getInstance());
            } else {
                builder = new CustomTabsIntent.Builder();
                builder.setStartAnimations(PostDetailsActivity.this, R.anim.slide_in_right, R.anim.slide_out_left);
                builder.setExitAnimations(PostDetailsActivity.this, R.anim.slide_in_left, R.anim.slide_out_right);
                builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                customTabsIntent = builder.build();

                try {
                    customTabsIntent.launchUrl(PostDetailsActivity.this, Uri.parse(mPostLink));
                    if (!tag.equals("1")) {
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            progressBar.setVisibility(View.GONE);


        }


        public String[] setJsoupModal(String website) {


            String[] id = new String[2];

            switch (website) {


                case " telugu.greatandhra.com":
                    id[0] = "page_news";
                    id[1] = "div.content";
                    return id;


                case " www.teluguglobal.in":

                    id[0] = "td-ss-main-content";
                    id[1] = "div.td-post-content p";
                    return id;


                case " www.neticinema.com":

                    id[0] = "site-content";
                    id[1] = "div";
                    return id;


                case " www.koradanews.com":


                    id[0] = "content";
                    id[1] = "p";
                    return id;


                case " www.tfpc.in":


                    id[0] = "entry";
                    id[1] = "p";
                    return id;


                case " www.apherald.com":


                    id[0] = "articledata";
                    id[1] = "div";
                    return id;


                case " www.mirchi9.com":


                    id[0] = "td-post-content";
                    id[1] = "p";
                    return id;


                case " prabhanews.com":


                    id[0] = "entry-content clearfix";
                    id[1] = "p";
                    return id;


                case " www.123telugu.com":


                    id[0] = "post-content";
                    id[1] = "p";
                    return id;

                case " telugu.oneindia.com":

                    id[0] = "leftpanel";
                    id[1] = "p";
                    return id;


                case " www.telugumirchi.com":


                    id[0] = "post-entry";
                    id[1] = "p";
                    return id;


                case " www.cinemabazaar.in":


                    id[0] = "entry-content herald-entry-content";
                    id[1] = "p";
                    return id;


                case " telugu.annnews.in":


                    id[0] = "col-lg-12 col-md-12 col-sm-12 col-xs-12 news-content no-gutters margin-top-15";
                    id[1] = "p";
                    return id;


                case " www.telugusamachar.com":


                    id[0] = "post-container cf";
                    id[1] = "div";
                    return id;


                case " www.adya.news":

                    id[0] = "td-post-content";
                    id[1] = "p";
                    return id;


                case " www.cinejosh.com":
                    id[0] = "arttextxml";


                    id[0] = "mobilePadding";
                    id[1] = "p";
                    return id;


                case " telugu.samayam.com":

                    id[0] = "article";
                    id[1] = "arttextxml";
                    return id;


                case " telugu.webdunia.com":

                    id[0] = "colL_MktColumn2";
                    id[1] = "";
                    return id;


                case " www.filmytollywood.com":


                    id[0] = "gallery-slider-inner";
                    id[1] = "div:first-child";
                    return id;


                case " www.telugumovies.com":
                    id[0] = "post";
                    id[1] = "p";
                    return id;

                case " telugu.gulte.com":
                    id[0] = "article";
                    id[1] = "div.content";
                    return id;


                case " www.andhravilas.net":

                    id[0] = "divMainDescription";
                    id[1] = "p";
                    return id;


                case " telugu.telugujournalist.com":

                    id[0] = "content-data";
                    id[1] = "p";
                    return id;


                case " www.vaartha.com":


                    id[0] = "entry-content clearfix";
                    id[1] = "p";
                    return id;


                case " telugu.thetelugufilmnagar.com":


                    id[0] = "col-md-12 wpb_wrapper";
                    id[1] = "p";
                    return id;


                case " www.mahaanews.com":
                    id[0] = "text-wrapper";
                    id[1] = "p";
                    return id;


                case " www.telugusquare.com":


                    id[0] = "entry_content";
                    id[1] = "p";
                    return id;


                case " visalaandhra.com":


                    id[0] = "entry";
                    id[1] = "p";
                    return id;


                case " apdunia.com":


                    id[0] = "entry";
                    id[1] = "p";
                    return id;


                case " www.eenadu.com":


                    id[0] = "two-col-left-block box-shadow telugu_uni_body offset-bt1 fullstory";
                    id[1] = "figure PDSAIFullStory p";
                    return id;


                case " telugu.ap2tg.com":


                    id[0] = "entry-content";
                    id[1] = "p";
                    return id;


                case " www.telugu360.com":


                    id[0] = "td-post-content";
                    id[1] = "p";
                    return id;


                case " www.telugucm.com":


                    id[0] = "entry-content col-md-12 col-md-push-0";
                    id[1] = "div:first-child";
                    return id;


                case " telugu.andhra99.com":


                    id[0] = "entry-content clearfix";
                    id[1] = "p";
                    return id;


                case " telugu.v6news.tv":


                    id[0] = "singlecon";
                    id[1] = "p";
                    return id;


                case " manatelangana.news":

                    id[0] = "post_content";
                    id[1] = "p";
                    return id;

                case " www.sakshi.com":

                    id[0] = "field-item even";
                    id[1] = "p";
                    return id;

                case " www.ntvtelugu.com":

                    id[0] = "td-post-content";
                    id[1] = "p";
                    return id;

                case " www.andhrajyothy.com":

                    id[0] = "article-content telugu-font fl";
                    id[1] = "div";
                    return id;

                case " www.tupaki.com":

                    id[0] = "descpt telugu";
                    id[1] = "p";
                    return id;


                case " telugu.filmibeat.com":

                    id[0] = "ecom-ad-content";
                    id[1] = "p";
                    return id;


            }

            return null;
        }


    }


}







