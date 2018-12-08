package com.tollywood24.tollywoodcircle.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.classes.ExpandableHeightGridView;
import com.tollywood24.tollywoodcircle.adapters.CustomGrid;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {


    WebView webView;
    private ProgressBar progressBar;
    private CustomTabsIntent.Builder builder;
    private CustomTabsIntent customTabsIntent;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new CustomTabsIntent.Builder();
        builder.setStartAnimations(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(getActivity(), R.anim.slide_in_left, R.anim.slide_out_right);
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        customTabsIntent = builder.build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fb, container, false);

        String[] website = {
                "Eenadu",
                "Sakshi",
                "Andhra Jhothy",
                "Vartha",
                "Great Andhra",
                "Tupaki",
                "Gulte",
                "cinejosh",
                "Apherald",
                "Mirch9",
                "Korada",
                "Samayam",
                "123telugu",
                "filmibeat",
                "telugumirchi",
                "andhravilas",
                "tolivelugu",
                "Amaravathi",
                "FilmyFocus"


        };


        String[] imageIdWeb = {
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzdAuWbpH1C-HcUyf1wUaFTLFxKyPsAW7NvtJfirP3RQnoWRiX",
                "https://lh3.googleusercontent.com/YOArLkNJAtSqvq2FSmu_efTKI3J8tQEq2gSAF73Tu_4chyrU7WnItv2xi3jiH6B_9nzp=w300",
                "https://d3l0sojrhpld4b.cloudfront.net/wp-content/uploads/2016/06/Andhra-Jyothi-does-it-again.jpg",
                "http://a382.phobos.apple.com/us/r30/Purple4/v4/c7/bd/a8/c7bda837-6174-2e1c-ceec-a21c6fbed257/mzl.hlcfximz.png",
                "http://www.apnewscorner.com/images/news/2013/01/3/Great-Andhra-e-paper-in-support-to-YSR-Congress.jpg",
                "https://lh3.googleusercontent.com/0OV56BaGpjcHVRHJB94gjKu_69pha2yPxTXZPHzpIzf-Wafi854mRupT_I8p-Fd3rHU=w300",
                "https://lh4.ggpht.com/K8kBPrGmohsW5uLGZOMtwM8wcdqsrdd6E5f08pad4er1Kr811_JdCXMRcnB_tpmUXw=w300",
                "http://www.cinejosh.com/images/logo.gif",
                "https://lh4.ggpht.com/FNlGQ3orSNWz4E4zeBVU2yNd4IxUN77O_v6dhNylVI5-MGLTT-tk_UZBE5hSHabiiVes=w300",
                "https://pbs.twimg.com/profile_images/885870701835636736/yCr7ABF5_400x400.jpg",
                "https://3.bp.blogspot.com/-PTdpM1bmPVQ/V2qKbiP7_zI/AAAAAAAAABo/y6-KENwU3HQ1aBZG4lksUWHr0pwqDwFgQCLcB/s1600/Korada-Logo.jpg",
                "https://lh3.googleusercontent.com/8V6UZvAuoD4gt-0NkmUWluXW5vDZAIDq0B2KqvG-IsY1KdiNhDas8flT_IfvYZz-Zw=w300",
                "https://pbs.twimg.com/profile_images/141909565/1_400x400.JPG",
                "https://photos.filmibeat.com/images/fb-banner.png",
                "http://www.telugumirchi.com/en/wp-content/uploads/2015/12/telugumirchi-logo-new.png",
                "http://www.jbepl.com/wp-content/uploads/2015/09/146.jpg",
                "https://pbs.twimg.com/profile_images/1599282457/Toli_Velugu_Logo_D02_4_SCloud_400x400.JPG",
                "http://www.amaravativoice.com/images/logo/AmaravatiVoiceLogo.png",
                "https://yt3.ggpht.com/a-/AJLlDp2H9oVx5Jcj5usp8J6BIiB0v2qwgcc8qX1VWQ=s900-mo-c-c0xffffffff-rj-k-no"

        };


        CustomGrid adapter2 = new CustomGrid(getActivity(), website, imageIdWeb);
        ExpandableHeightGridView grid_websites = (ExpandableHeightGridView) view.findViewById(R.id.grid_websites);
        grid_websites.setAdapter(adapter2);
        grid_websites.setExpanded(true);


        grid_websites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                switch (position) {

                    case 0:

                        openChromeTab("http://www.eenadu.net/");
                        break;
                    case 1:
                        openChromeTab("http://www.sakshi.com/");
                        break;
                    case 2:
                        openChromeTab("http://www.andhrajyothy.com");
                        break;
                    case 3:
                        openChromeTab("http://www.vaartha.com");
                        break;

                    case 4:
                        openChromeTab("http://telugu.greatandhra.com");
                        break;
                    case 5:
                        openChromeTab("http://www.tupaki.com");
                        break;

                    case 6:
                        openChromeTab("http://m.gulte.com/telugu");
                        break;

                    case 7:

                        openChromeTab("http://www.cinejosh.com/telugu-root.html");
                        break;
                    case 8:

                        openChromeTab("http://www.apherald.com/movies/te");
                        break;

                    case 9:
                        openChromeTab("https://www.mirchi9.com/tag/telugu-movies");
                        break;

                    case 10:
                        openChromeTab("http://www.koradanews.com/telugu/category/movies");
                        break;

                    case 11:
                        openChromeTab("https://telugu.samayam.com/telugu-movies/cinema-news/articlelist/47120426.cms");
                        break;

                    case 12:
                        openChromeTab("http://www.123telugu.com/telugu/");
                        break;

                    case 13:
                        openChromeTab("https://telugu.filmibeat.com/");
                        break;

                    case 14:
                        openChromeTab("http://www.telugumirchi.com/te/movies");
                        break;

                    case 15:
                        openChromeTab("http://www.andhravilas.net/te/Categories/latest-news/.html");
                        break;

                    case 16:
                        openChromeTab("http://telugu.tolivelugu.com/");
                        break;

                    case 17:
                        openChromeTab("http://www.amaravativoice.com/");
                        break;

                    case 18:
                        openChromeTab("http://filmyfocus.com/telugu");
                        break;


                }


            }
        });


        return view;

    }




    public void openChromeTab(String url) {
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }

}



