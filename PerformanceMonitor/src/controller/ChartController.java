package controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.util.Rotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import model.*;


@Controller
@RequestMapping("/charts")
public class ChartController {
	
	
	//from the trial jsp after putting the c:url tag ,
	//below methods of piechart,piechart1,piechart2 are all working fine
	//c:url was the key :)

	/*
	 * Pie chart trials 
	 */
	  
	@RequestMapping(value="/pieChart",method=RequestMethod.GET)
	public void drawPieChart(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			PieDataset pd = createDataset();
			JFreeChart chart = createChart(pd, "Pie Chart");
			response.setContentType("image/png");
			OutputStream out=response.getOutputStream();
			ChartUtilities.writeChartAsPNG(out,chart,750,400);
			response.getOutputStream().close();
		}

		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}

	@RequestMapping(value="/pieChart1",method=RequestMethod.GET)
	@ResponseBody
	public byte[] drawPieChart1()
	{
		byte[] bytearray =null;
		PieDataset pd = createDataset();
		JFreeChart chart = createChart(pd, "Pie Chart");
		ByteArrayOutputStream  bos = new ByteArrayOutputStream();
		try
		{
			ChartUtilities.writeChartAsPNG(bos,chart,750,400);

			bytearray = ChartUtilities.encodeAsPNG(chart.createBufferedImage(750,400));
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		bytearray = bos.toByteArray();	
		return bytearray;

	}


	@RequestMapping(value="/pieChart2",method=RequestMethod.GET)
	public void drawPieChart2(HttpServletResponse response,HttpServletRequest request)
	{
		response.setContentType("image/jpeg");
		PieDataset pd = createDataset();
		JFreeChart chart = createChart(pd, "Pie Chart");
		try
		{
		OutputStream out = response.getOutputStream();
		ChartUtilities.writeChartAsJPEG(out , chart , 400, 400);
		out.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}

	}


	private PieDataset createDataset() {
		DefaultPieDataset dpd = new DefaultPieDataset();

		dpd.setValue("Entertaintment", 57);
		dpd.setValue("Travel", 23);
		dpd.setValue("Others", 20);
		return dpd;
	}

	private JFreeChart createChart(PieDataset pd,String title)
	{
		JFreeChart chart = ChartFactory.createPieChart3D(title, pd,true,true,false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(270);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
	
	/*
	 * Line chart trials
	 */
	
	@RequestMapping(value="/lineChartDemo",method=RequestMethod.GET)
	public void drawLineChart(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();
		    line_chart_dataset.addValue( 15 , "a" , "1970" );
		    line_chart_dataset.addValue( 30 , "a" , "1980" );
		    line_chart_dataset.addValue( 60 , "a" , "1990" );
		    line_chart_dataset.addValue( 120 , "a" , "2000" );
		    line_chart_dataset.addValue( 240 , "a" , "2010" ); 
		    line_chart_dataset.addValue( 300 , "a" , "2014" );
		    line_chart_dataset.addValue(199, "a", "2015");
		    //a could be replaced by any other keyword, but all should have the same keyword
		    //otherwise the one with different keyword will not be displayed appropriately


		    JFreeChart lineChartObject = ChartFactory.createLineChart(
		    	       "ProcessUsage information","Time",
		    	       "Resource usage",
		    	       line_chart_dataset,PlotOrientation.VERTICAL,
		    	       true,true,false);
		    int  width= 640; /* Width of the image */
		    int  height= 480; /* Height of the image */ 
			response.setContentType("image/png");
			OutputStream out=response.getOutputStream();
			ChartUtilities.writeChartAsPNG(out,lineChartObject,width,height);
			response.getOutputStream().close();
		}

		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	/*
	 *Time series chart trial
	 */
	
	@RequestMapping(value="/timeSeriesChartDemo",method=RequestMethod.GET)
	public void drawTimeSeriesChart(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			final TimeSeries series = new TimeSeries( "Random Data" );			
			Second current = new Second();
			double value = 100.0;
			
			for ( int i = 0 ; i < 4000 ; i++ )
		    {
		       try
		       {
		          value = value + Math.random( ) - 0.5;
		          series.add( current , new Double( value ) );
		          current = ( Second ) current.next( );
		       }
		       catch ( SeriesException e ) 
		       {
		          System.err.println( "Error adding to series" );
		       }
		    }
			final XYDataset dataset=( XYDataset )new TimeSeriesCollection(series);		
			JFreeChart timechart = ChartFactory.createTimeSeriesChart(
				       "Computing Test", 
				       "Seconds", 
				       "Value", 
				       dataset,
				       false, 
				       false, 
				       false);
		    int  width= 640; /* Width of the image */
		    int  height= 480; /* Height of the image */ 
			response.setContentType("image/png");
			OutputStream out=response.getOutputStream();
			ChartUtilities.writeChartAsPNG(out,timechart,width,height);
			response.getOutputStream().close();
		}

		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}
	

	@RequestMapping(value="/timeSeriesChartDemo1",method=RequestMethod.GET)
	public void drawTimeSeriesChart1(HttpServletResponse response,HttpServletRequest request)
	{
		try
		{
			final TimeSeries memSeries = new TimeSeries( "memory Usage" );			
			Second current = new Second();
			int value=2;
			for ( int i = 0 ; i < 4 ; i++ )
		    {
		       try
		       {
		          value *=2 ;
		          memSeries.add( current , value );
		          current = ( Second ) current.next( );
		       }
		       catch ( SeriesException e ) 
		       {
		          System.err.println( "Error adding to series" );
		       }
		    }
			final XYDataset dataset=( XYDataset )new TimeSeriesCollection(memSeries);		
			JFreeChart timechart = ChartFactory.createTimeSeriesChart(
				       "Memory Usage", 
				       "Seconds", 
				       "Usage", 
				       dataset,
				       false, 
				       false, 
				       false);
		    int  width= 640; /* Width of the image */
		    int  height= 480; /* Height of the image */ 
			response.setContentType("image/png");
			OutputStream out=response.getOutputStream();
			ChartUtilities.writeChartAsPNG(out,timechart,width,height);
			response.getOutputStream().close();
		}

		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="/memoryUsageChart/{processName}",method=RequestMethod.GET)
	public void drawTimeSeriesChartMemoryUsage(HttpServletResponse response,@PathVariable("processName")String processName)
	{
		ProcessController processController = new ProcessController();
		
			System.out.println("In the charting function process name is"+processName);
			//get values for this process from db
			//form static chart of the db values.
			ArrayList<DBProcessInfo> processUsageHistoryList = 
					processController.getASingleProcessUsageHistory(processName);

			final TimeSeries memSeriesHistory = new TimeSeries( "memory usage history" );			
			Second current = new Second();

			try
			{
				for(int i=0;i<processUsageHistoryList.size();i++)
				{
					memSeriesHistory.add( current , Double.parseDouble(processUsageHistoryList.get(i).getMemSize()) );
					current = ( Second ) current.next( );
				}
			
		    }
			catch ( SeriesException e ) 
			{
				System.err.println( "Error adding to series" );
			}
		
		final XYDataset dataset=( XYDataset )new TimeSeriesCollection(memSeriesHistory);		
		JFreeChart timechart = ChartFactory.createTimeSeriesChart(
				"Memory Usage", 
				"Seconds", 
				"Usage", 
				dataset,
				false, 
				false, 
				false);
		int  width= 640; // Width of the image 
		int  height= 480; // Height of the image  
		response.setContentType("image/png");
		try
		{
		OutputStream out=response.getOutputStream();
		ChartUtilities.writeChartAsPNG(out,timechart,width,height);
		response.getOutputStream().close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		}
	
	@RequestMapping(value="/memoryUsageChartLive/{processName}",method=RequestMethod.GET)
	public void drawTimeSeriesChartMemoryUsageLive(HttpServletResponse response,@PathVariable("processName")String processName)
	{
		ProcessController processController = new ProcessController();
			ArrayList<DBProcessInfo> processUsageHistoryList = 
					processController.getASingleProcessUsageHistory(processName);

			final TimeSeries memSeriesHistory = new TimeSeries( "memory usage history" );			
			Second current = new Second();

			try
			{
				for(int i=0;i<processUsageHistoryList.size();i++)
				{
					memSeriesHistory.add( current , Double.parseDouble(processUsageHistoryList.get(i).getMemSize()) );
					current = ( Second ) current.next( );
				}
			
		    }
			catch ( SeriesException e ) 
			{
				System.err.println( "Error adding to series" );
			}
		
		final XYDataset dataset=( XYDataset )new TimeSeriesCollection(memSeriesHistory);		
		JFreeChart timechart = ChartFactory.createTimeSeriesChart(
				"Memory Usage", 
				"Seconds", 
				"Usage", 
				dataset,
				false, 
				false, 
				false);
		int  width= 640; // Width of the image 
		int  height= 480; // Height of the image  
		response.setContentType("image/png");
		try
		{
		OutputStream out=response.getOutputStream();
		ChartUtilities.writeChartAsPNG(out,timechart,width,height);
		response.getOutputStream().close();
		}
		catch(Exception e)
		{
			System.out.println(e.getLocalizedMessage());
		}
		}
	
	
	
}   
   

    

	

