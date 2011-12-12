package basis;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.StreamConnection;

public abstract class Connection extends StoppableThread {

	protected StreamConnection connection;
	protected DataInputStream inputStream;
	protected DataOutputStream outputStream;

	public Connection() {
	}

	public Connection(StreamConnection connection) {
		openConnection(connection);
	}

	protected void openConnection(StreamConnection connection) {
		this.connection = connection;
		this.inputStream = connection.openDataInputStream();
		this.outputStream = connection.openDataOutputStream();
	}

	protected abstract void parseFlag(Flag flag);

	@Override
	protected void doFinally() {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (IOException ex) {
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (IOException ex) {
		}

		connection = null;
	}

	public void sendFlag(Flag flag) {
		try {
			outputStream.writeInt(flag.ordinal());
			outputStream.flush();
		} catch (IOException ex) {
			setStopped(true);
		}
	}

	@Override
	protected void doAction() {
		int i = -1;
		try {
			i = inputStream.readInt();
			if (i >= 0) {
				if (i < Flag.values().length) {
					Flag flag = Flag.values()[i];
					parseFlag(flag);
				}
			} else {
				setStopped(true);
			}
		} catch (IOException ex) {
			setStopped(true);
		}
	}

}
