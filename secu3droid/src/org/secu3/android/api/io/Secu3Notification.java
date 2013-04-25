package org.secu3.android.api.io;

import org.secu3.android.MainActivity;
import org.secu3.android.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class Secu3Notification {
	public Notification secu3Notification;
	public Notification connectionProblemNotification;
	public Notification serviceStoppedNotification;
	public NotificationManager notificationManager;
	
	private Context ctx;
	
	public Secu3Notification(Context ctx) {
		this.ctx = ctx;
		notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);			
		
		secu3Notification = new NotificationCompat.Builder(ctx)
			.setContentTitle(ctx.getString(R.string.foreground_service_started_notification))
			.setSmallIcon(R.drawable.ic_launcher)											
			.setWhen(System.currentTimeMillis())
			.setOngoing(true)
			.setContentIntent(PendingIntent.getActivity(ctx, 0, new Intent (ctx,MainActivity.class), Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT))
			.build();					
		
		connectionProblemNotification = new NotificationCompat.Builder(ctx)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(PendingIntent.getActivity(ctx, 0, new Intent (ctx,MainActivity.class), Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT))
			.build();		

		serviceStoppedNotification = new NotificationCompat.Builder(ctx)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentIntent(PendingIntent.getActivity(ctx, 0, new Intent (ctx,MainActivity.class), Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT))
			.build();		
	}
	
	public void notifyConnectionProblem (int maxConnectionRetries, int nbRetriesRemaining) {
		String pbMessage = ctx.getResources().getQuantityString(R.plurals.connection_problem_notification, nbRetriesRemaining, nbRetriesRemaining);
		
		connectionProblemNotification = new NotificationCompat.Builder (ctx)		
				.setContentTitle(ctx.getString(R.string.connection_problem_notification_title))
				.setContentText(pbMessage)
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.drawable.ic_launcher)
				.setOngoing(true)
				.setContentIntent(connectionProblemNotification.contentIntent)
				.setNumber(1 + maxConnectionRetries - nbRetriesRemaining)
				.build();				
		notificationManager.notify(R.string.connection_problem_notification_title, connectionProblemNotification);			
	}
	
	public void notifyServiceStopped (int disableReason) {
		serviceStoppedNotification = new NotificationCompat.Builder(ctx)
				.setContentTitle(ctx.getString(R.string.service_closed_because_connection_problem_notification_title))
				.setContentText(ctx.getString(R.string.service_closed_because_connection_problem_notification, ctx.getString(disableReason)))
				.setWhen(System.currentTimeMillis())
				.setSmallIcon(R.drawable.ic_launcher)
				.setOngoing(true)
				.setContentIntent(serviceStoppedNotification.contentIntent)
				.build();
		notificationManager.notify(R.string.service_closed_because_connection_problem_notification_title, serviceStoppedNotification);		
	}
	
	
}
