package com.appxray;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    private final List<String> lines = new ArrayList<>();
    private TextView output;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout page = new LinearLayout(this); page.setOrientation(LinearLayout.VERTICAL); page.setPadding(24,28,24,20); page.setBackgroundColor(0xFF101820);
        TextView title = new TextView(this); title.setText("APP X-RAY"); title.setTextSize(28); title.setTextColor(0xFF65D6AD); title.setGravity(Gravity.CENTER_VERTICAL); page.addView(title);
        TextView desc = new TextView(this); desc.setText("On-device installed-app inventory and declared-permission review. No Internet permission; no data is uploaded."); desc.setTextColor(0xFFE6EDF3); desc.setPadding(0,10,0,18); page.addView(desc);
        Button scan = new Button(this); scan.setText("Scan installed apps"); page.addView(scan);
        Button share = new Button(this); share.setText("Share scan report"); page.addView(share);
        ScrollView scroll = new ScrollView(this); output = new TextView(this); output.setTextColor(0xFFE6EDF3); output.setTextSize(13); output.setText("Tap Scan installed apps to begin."); scroll.addView(output); page.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        scan.setOnClickListener(v -> scanApps()); share.setOnClickListener(v -> shareReport()); setContentView(page);
    }
    private void scanApps() {
        lines.clear(); lines.add("APP X-RAY REPORT"); lines.add("Generated: "+new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(new Date())); lines.add("Local inventory. Declared permissions do not prove actual use."); lines.add("");
        PackageManager pm=getPackageManager(); List<PackageInfo> installed=pm.getInstalledPackages(PackageManager.GET_PERMISSIONS); int total=0;
        for(PackageInfo pkg:installed){ ApplicationInfo ai=pkg.applicationInfo; if(ai==null) continue; boolean system=(ai.flags&ApplicationInfo.FLAG_SYSTEM)!=0; lines.add((system?"[SYSTEM] ":"[USER] ")+pm.getApplicationLabel(ai)); lines.add("Package: "+pkg.packageName); lines.add("Version: "+(pkg.versionName==null?"unknown":pkg.versionName)); String[] permissions=pkg.requestedPermissions;
            if(permissions==null||permissions.length==0) lines.add("Declared permissions: none listed"); else { lines.add("Declared permissions:"); for(String p:permissions) lines.add("  - "+p.substring(p.lastIndexOf('.')+1)); } lines.add(""); total++; }
        StringBuilder result=new StringBuilder("Scanned "+total+" packages.\n\n"); for(String line:lines) result.append(line).append('\n'); output.setText(result.toString()); Toast.makeText(this,"Scan complete: "+total+" apps",Toast.LENGTH_SHORT).show();
    }
    private void shareReport(){ if(lines.isEmpty()){Toast.makeText(this,"Run a scan first.",Toast.LENGTH_SHORT).show();return;} StringBuilder body=new StringBuilder(); for(String line:lines)body.append(line).append('\n'); Intent i=new Intent(Intent.ACTION_SEND); i.setType("text/plain"); i.putExtra(Intent.EXTRA_SUBJECT,"App X-Ray report"); i.putExtra(Intent.EXTRA_TEXT,body.toString()); startActivity(Intent.createChooser(i,"Share report")); }
}
