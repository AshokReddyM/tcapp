package com.tollywood24.tollywoodcircle.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.tollywood24.tollywoodcircle.R;
import com.tollywood24.tollywoodcircle.classes.ExpandableHeightGridView;
import com.tollywood24.tollywoodcircle.adapters.CustomGrid;

/**
 * A simple {@link Fragment} subclass.
 */
public class EpaperFragment extends Fragment {

    private CustomTabsIntent.Builder builder;
    private CustomTabsIntent customTabsIntent;
    private InterstitialAd mInterstitialAd;


    public EpaperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new CustomTabsIntent.Builder();
        builder.setShowTitle(false);
        builder.setStartAnimations(getActivity(), R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(getActivity(), R.anim.slide_in_left, R.anim.slide_out_right);
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        customTabsIntent = builder.build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_epaper, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] website = {
                "Eenadu",
                "Sakshi",
                "Andhra Jhothy",
                "Vartha",
                "Andhra Prabha",
                "Surya",
                "Praja Sakthi",
                "Andhra Bhoomi"

        };


        String[] imageIdWeb = {
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzdAuWbpH1C-HcUyf1wUaFTLFxKyPsAW7NvtJfirP3RQnoWRiX",
                "https://lh3.googleusercontent.com/YOArLkNJAtSqvq2FSmu_efTKI3J8tQEq2gSAF73Tu_4chyrU7WnItv2xi3jiH6B_9nzp=w300",
                "https://d3l0sojrhpld4b.cloudfront.net/wp-content/uploads/2016/06/Andhra-Jyothi-does-it-again.jpg",
                "http://a382.phobos.apple.com/us/r30/Purple4/v4/c7/bd/a8/c7bda837-6174-2e1c-ceec-a21c6fbed257/mzl.hlcfximz.png",
                "https://lh3.googleusercontent.com/PXJcarkQB1GKMrBWGN_MoCScJlsT98dt3cLu2hMIBqdKxaTlXilH2kRQ0DNPLTv1KDE=s180",
                "https://pbs.twimg.com/profile_images/938663787745255424/dTlAlz7K_400x400.jpg",
                "https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/3e/de/1d/3ede1d47-d7d2-98e5-f60b-3cbd28a52a7e/source/512x512bb.jpg",
                "https://lh3.googleusercontent.com/fFHHExEdNwuu5peSWrqAJoN-K_evNFEAkUSUwR8V2SDS3iXGg3B2RzWyHOp6DcjGsQc=w300"


        };


        CustomGrid adapter2 = new CustomGrid(getActivity(), website, imageIdWeb);
        ExpandableHeightGridView grid_websites = (ExpandableHeightGridView) getActivity().findViewById(R.id.grid_websites);
        grid_websites.setAdapter(adapter2);
        grid_websites.setExpanded(true);


        grid_websites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                switch (position) {

                    case 0:

                        openChromeTab("http://epaper.eenadu.net/");
                        break;
                    case 1:
                        openChromeTab("http://epaper.sakshi.com");
                        break;
                    case 2:
                        openChromeTab("http://epaper.andhrajyothy.com");
                        break;
                    case 3:
                        openChromeTab("http://epaper.vaartha.com/");
                        break;
                    case 4:
                        openChromeTab("http://epaper.prabhanews.com/");
                        break;
                    case 5:
                        openChromeTab("http://epaper.suryaa.com/");
                        break;
                    case 6:
                        openChromeTab("http://epaper.prajasakti.com/");
                        break;
                    case 7:
                        openChromeTab("http://epaper.andhrabhoomi.net/andhrabhoomi.aspx?id=AP");
                        break;


                }


            }
        });
    }


    public void openChromeTab(String url) {
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));

        // Create the InterstitialAd and set the adUnitId.
        mInterstitialAd = new InterstitialAd(getActivity());
        // Defined in res/values/strings.xml
        mInterstitialAd.setAdUnitId("ca-app-pub-3877906654321071/6270778947");

        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }
        });
    }


}



