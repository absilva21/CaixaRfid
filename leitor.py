#import mercury
import sys
import socket
"""
param = 2300

if len(sys.argv) > 1:
        param = int(sys.argv[1])
#configurando
reader = mercury.Reader("tmr:///dev/ttyUSB0")

#setando regiao
reader.set_region("NA2")

reader.set_read_plan([1], "GEN2", read_power=param)

epcs = map(lambda tag: tag, reader.read())

tags = "" 

for tag in epcs:
	tags +=  tag.epc.decode("utf-8") + "\n"

print(tags)
"""

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind(("192.168.0.111",70))
s.listen(30)
print("servidor ligado")
while True:
        conn, addr = s.accept()
        data = conn.recv(1024)
        print("cliente: " + addr[0] )
        print(data)

        if data == b'read':
                conn.sendall(b'E2000017221100961890544A\n'+
                        b'E2000017221101321890548C\n'+
                        b'E20000172211009418905449\n'+
                        b'E20000172211010118905454\n'+
                        b'E20000172211011718905474\n'+
                        b'E20000172211010218905459\n'+
                        b'E20000172211012518905484\n'+
                        b'E2000017221101241890547C\n'+
                        b'E20000172211011118905471\n')
        conn.close()
