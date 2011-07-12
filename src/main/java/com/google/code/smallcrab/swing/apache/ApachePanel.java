/**
 * 
 */
package com.google.code.smallcrab.swing.apache;

import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.AGENT;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.ALL;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.CODE;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.DOMAIN;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.IP;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.METHOD;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.PATH;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.QUERY;
import static com.google.code.smallcrab.swing.apache.ApacheConfigTableModel.REFERRER;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.mapper.apache.ApacheCountMapper;
import com.google.code.smallcrab.matcher.apache.ApacheAgentMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheCodeMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheDomainMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheIPMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheLogLineMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheMethodMatcher;
import com.google.code.smallcrab.matcher.apache.ApachePathMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheQueryMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheReferrerMatcher;
import com.google.code.smallcrab.reducer.FileLineAnalyzer;
import com.google.code.smallcrab.swing.AnalyzeConfigPanel;
import com.google.code.smallcrab.swing.utils.ColumnResizer;
import com.google.code.smallcrab.utils.StringKit;
import com.google.code.smallcrab.viewer.apache.ApacheAgentViewer;
import com.google.code.smallcrab.viewer.apache.ApacheAllViewer;
import com.google.code.smallcrab.viewer.apache.ApacheCodeViewer;
import com.google.code.smallcrab.viewer.apache.ApacheDomainViewer;
import com.google.code.smallcrab.viewer.apache.ApacheIPViewer;
import com.google.code.smallcrab.viewer.apache.ApacheLogLineViewer;
import com.google.code.smallcrab.viewer.apache.ApacheMethodViewer;
import com.google.code.smallcrab.viewer.apache.ApachePathViewer;
import com.google.code.smallcrab.viewer.apache.ApacheQueryViewer;
import com.google.code.smallcrab.viewer.apache.ApacheReferrerViewer;

/**
 * Apache log analyze config panel.
 * 
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class ApachePanel extends AnalyzeConfigPanel<ApacheLogLineViewer, ApacheLogLineMatcher> {

	private static final long serialVersionUID = 2761139314517046734L;

	private JTable apacheViewTable;

	private JTable apacheMatchTable;

	private JLabel apacheConfigOutput;

	public ApachePanel() {
		super(new GridBagLayout());
		setName("apache/iis");

		GridBagConstraints vlc = new GridBagConstraints();
		vlc.fill = GridBagConstraints.VERTICAL;
		vlc.gridx = 0;
		vlc.gridy = 0;
		add(new JLabel("view config", SwingConstants.LEFT), vlc);

		GridBagConstraints vc = new GridBagConstraints();
		vc.fill = GridBagConstraints.VERTICAL;
		vc.gridx = 0;
		vc.gridy = 1;
		this.apacheViewTable = new ApacheViewConfigTable();
		ColumnResizer.setFixColumnWidth(this.apacheViewTable, 1, 200);
		add(this.apacheViewTable, vc);

		GridBagConstraints mlc = new GridBagConstraints();
		mlc.fill = GridBagConstraints.VERTICAL;
		mlc.gridx = 0;
		mlc.gridy = 2;
		add(new JLabel("match config", SwingConstants.LEFT), mlc);

		GridBagConstraints mc = new GridBagConstraints();
		mc.fill = GridBagConstraints.VERTICAL;
		mc.gridx = 0;
		mc.gridy = 3;
		this.apacheMatchTable = new ApacheMatchConfigTable();
		ColumnResizer.setFixColumnWidth(this.apacheMatchTable, 1, 200);
		add(this.apacheMatchTable, mc);

		GridBagConstraints mcerror = new GridBagConstraints();
		mcerror.fill = GridBagConstraints.VERTICAL;
		mcerror.gridx = 0;
		mcerror.gridy = 4;
		this.apacheConfigOutput = new JLabel();
		this.apacheConfigOutput.setForeground(Color.RED);
		this.apacheConfigOutput.setBackground(Color.LIGHT_GRAY);
		this.apacheConfigOutput.setPreferredSize(new Dimension(300, 20));
		this.resetConfigOutput();
		add(this.apacheConfigOutput, mcerror);

	}

	@Override
	protected void notifyFileChange(File logFile) throws IOException {
		
	}

	public void resetConfigOutput() {
		this.apacheConfigOutput.setText("");
	}

	/**
	 * @return
	 * @throws ConfigException
	 * 
	 */
	protected List<ApacheLogLineViewer> prepareViewers() {
		List<ApacheLogLineViewer> lineViewers = new ArrayList<ApacheLogLineViewer>();
		TableModel viewModel = this.apacheViewTable.getModel();
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			String option = (String) viewModel.getValueAt(rowIndex, 0);
			String value = (String) viewModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			ApacheLogLineViewer viewer = null;
			if (DOMAIN.equals(option)) {
				viewer = new ApacheDomainViewer(value);
			} else if (QUERY.equals(option)) {
				viewer = new ApacheQueryViewer(value);
			} else if (PATH.equals(option)) {
				viewer = new ApachePathViewer(value);
			} else if (REFERRER.equals(option)) {
				viewer = new ApacheReferrerViewer(value);
			} else if (CODE.equals(option)) {
				viewer = new ApacheCodeViewer(value);
			} else if (METHOD.equals(option)) {
				viewer = new ApacheMethodViewer(value);
			} else if (AGENT.equals(option)) {
				viewer = new ApacheAgentViewer(value);
			} else if (IP.equals(option)) {
				viewer = new ApacheIPViewer(value);
			} else if (ALL.equals(option)) {
				viewer = new ApacheAllViewer(value);
			}
			lineViewers.add(viewer);
		}
		return lineViewers;
	}

	protected List<ApacheLogLineMatcher> prepareMatchers() {
		List<ApacheLogLineMatcher> lineMatchers = new ArrayList<ApacheLogLineMatcher>();
		TableModel matchModel = this.apacheMatchTable.getModel();
		for (int rowIndex = 0; rowIndex < matchModel.getRowCount(); rowIndex++) {
			String option = (String) matchModel.getValueAt(rowIndex, 0);
			String value = (String) matchModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) matchModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			ApacheLogLineMatcher matcher = null;
			if (DOMAIN.equals(option)) {
				matcher = new ApacheDomainMatcher(value);
			} else if (QUERY.equals(option)) {
				String[] kv = StringKit.split(value, '=');
				matcher = new ApacheQueryMatcher(kv[0], kv[1]);
			} else if (PATH.equals(option)) {
				matcher = new ApachePathMatcher(value);
			} else if (REFERRER.equals(option)) {
				matcher = new ApacheReferrerMatcher(value);
			} else if (CODE.equals(option)) {
				matcher = new ApacheCodeMatcher(value);
			} else if (METHOD.equals(option)) {
				matcher = new ApacheMethodMatcher(value);
			} else if (AGENT.equals(option)) {
				matcher = new ApacheAgentMatcher(value);
			} else if (IP.equals(option)) {
				matcher = new ApacheIPMatcher(value);
			}
			lineMatchers.add(matcher);
		}
		return lineMatchers;
	}

	private void outputMatchConfigError(String option, String value) {
		String output = null;
		if (option != null) {
			output = String.format("Please check match config [option:%s,value:%s].", option, value);
		} else {
			output = "Please check match configs.";
		}
		this.apacheConfigOutput.setText(output);
	}

	private void outputViewConfigError(String option, String value) {
		String output = null;
		if (option != null) {
			output = String.format("Please check view config [option:%s,value:%s].", option, value);
		} else {
			output = "Please check view configs.";
		}
		this.apacheConfigOutput.setText(output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepareScanner()
	 */
	@Override
	public FileLineAnalyzer createFileLineAnalyzer() {
		ApacheCountMapper scanner = new ApacheCountMapper();
		scanner.setLineViewers(this.prepareViewers());
		scanner.setLineMatchers(this.prepareMatchers());
		FileLineAnalyzer analyzer = new FileLineAnalyzer(scanner);
		return analyzer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.swing.AnalyzeConfigPanel#prepared()
	 */
	@Override
	public boolean isPrepared() throws ConfigException {
		// check if view is prepared
		TableModel viewModel = this.apacheViewTable.getModel();
		int checkedNum = 0;
		for (int rowIndex = 0; rowIndex < viewModel.getRowCount(); rowIndex++) {
			String option = (String) viewModel.getValueAt(rowIndex, 0);
			String value = (String) viewModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) viewModel.getValueAt(rowIndex, 2);
			if (checked)
				checkedNum++;
			if (DOMAIN.equals(option)) {
			} else if (QUERY.equals(option)) {
			} else if (PATH.equals(option)) {
			} else if (REFERRER.equals(option)) {
			} else if (CODE.equals(option)) {
			} else if (METHOD.equals(option)) {
			} else if (AGENT.equals(option)) {
			} else if (IP.equals(option)) {
			} else if (ALL.equals(option)) {
			} else {
				outputViewConfigError(option, value);
				return false;
			}
		}
		if (checkedNum == 0) {
			outputViewConfigError(null, null);
			return false;
		}
		// check if config is prepared
		TableModel matchModel = this.apacheMatchTable.getModel();
		for (int rowIndex = 0; rowIndex < matchModel.getRowCount(); rowIndex++) {
			String option = (String) matchModel.getValueAt(rowIndex, 0);
			String value = (String) matchModel.getValueAt(rowIndex, 1);
			boolean checked = (Boolean) matchModel.getValueAt(rowIndex, 2);
			if (!checked) {
				continue;
			}
			if (StringKit.isEmpty(value)) {
				outputMatchConfigError(option, value);
				return false;
			}
			if (DOMAIN.equals(option)) {
			} else if (QUERY.equals(option)) {
				String[] kv = StringKit.split(value, '=');
				if (kv.length != 2) {
					outputMatchConfigError(option, value);
					return false;
				}
			} else if (PATH.equals(option)) {
			} else if (REFERRER.equals(option)) {
			} else if (CODE.equals(option)) {
			} else if (METHOD.equals(option)) {
			} else if (AGENT.equals(option)) {
			} else if (IP.equals(option)) {
			} else {
				outputMatchConfigError(option, value);
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isAnalyzeCount() {
		return true;
	}

}
