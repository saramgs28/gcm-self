package com.andresrcb.gcmbackend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.FileInfo;

import com.andresrcb.gcmbackend.FileRecord;
import com.google.appengine.api.files.FileServicePb;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

import org.json.simple.JSONObject;

public class UploadEndpoint extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String toPhone = req.getParameter("toPhone");
        String fromPhone = req.getParameter("fromPhone");
        MessageRecord record = new MessageRecord();
        List<BlobKey> blobs = blobstoreService.getUploads(req).get("file");
        BlobKey blobKey = blobs.get(0);

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingUrlOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

        String servingUrl = imagesService.getServingUrl(servingUrlOptions);
        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("application/json");
        JSONObject json = new JSONObject();
        json.put("servingUrl", servingUrl);
        json.put("blobKey", blobKey.getKeyString());
        record.setFileId(blobKey.getKeyString());
        record.setFromPhone(fromPhone);
        record.setToPhone(toPhone);
        PrintWriter out = res.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();

    }
}
