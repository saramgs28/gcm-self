package com.andresrcb.gcmbackend;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

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


import org.json.simple.JSONObject;
import static com.andresrcb.gcmbackend.OfyService.ofy;


public class ServerBlobEndpoint extends HttpServlet{
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{

        String fileId = req.getParameter("fileId");
        String fromPhone = req.getParameter("fromPhone");
        BlobKey blobKey = new BlobKey(fileId);
        blobstoreService.serve(blobKey, res);
    }
}
